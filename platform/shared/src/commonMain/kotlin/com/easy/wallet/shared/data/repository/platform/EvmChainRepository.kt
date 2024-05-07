package com.easy.wallet.shared.data.repository.platform

import com.easy.wallet.core.commom.cleanHexPrefix
import com.easy.wallet.core.commom.ifNullOrBlank
import com.easy.wallet.model.asset.AssetCoin
import com.easy.wallet.model.asset.AssetPlatform
import com.easy.wallet.network.source.etherscan.EtherscanApi
import com.easy.wallet.network.source.evm_rpc.JsonRpcApi
import com.easy.wallet.shared.asHex
import com.easy.wallet.shared.model.fees.EthereumFee
import com.easy.wallet.shared.model.fees.FeeLevel
import com.easy.wallet.shared.model.fees.FeeModel
import com.easy.wallet.shared.model.transaction.TransactionUiModel
import com.easy.wallet.shared.model.transaction.asTransactionUiModel
import com.ionspin.kotlin.bignum.integer.toBigInteger
import com.trustwallet.core.AnySigner
import com.trustwallet.core.CoinType
import com.trustwallet.core.ethereum.SigningInput
import com.trustwallet.core.ethereum.SigningOutput
import com.trustwallet.core.ethereum.Transaction
import com.trustwallet.core.ethereum.TransactionMode
import com.trustwallet.core.sign
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import okio.ByteString

class EvmChainRepository internal constructor(
    private val etherscanApi: EtherscanApi,
    private val jsonRpcApi: JsonRpcApi
) : OnChainRepository {
    override suspend fun loadBalance(
        platform: AssetPlatform,
        account: String,
        contract: String?
    ): String {
        val rpcUrl = platform.network!!.rpcUrl
        val balance = jsonRpcApi.getBalance(rpcUrl, account, contract)
        return balance.cleanHexPrefix().toBigInteger(16).toString()
    }

    override suspend fun loadTransactions(
        coin: AssetCoin,
        page: Int,
        offset: Int
    ): List<TransactionUiModel> {
        val account = coin.address
        val isContract = !coin.contract.isNullOrBlank()
        val basicTransactions = if (isContract) {
            etherscanApi.getContractInternalTransactions(
                page,
                offset,
                account,
                coin.contract.orEmpty()
            )
        } else {
            etherscanApi.getTransactions(page, offset, account)
        }
        return basicTransactions.map { it.asTransactionUiModel(coin, -coin.decimalPlace, account) }
    }

    override suspend fun prepFees(
        coin: AssetCoin,
        toAddress: String,
        amount: String
    ): List<FeeModel> = withContext(Dispatchers.Default) {
        val estimateGasDeferred = async { estimateGas(coin.platform, coin.address, toAddress, coin.contract, amount) }
        val feeDeferred = async { calculateFee(coin.platform) }

        val estimateGas = estimateGasDeferred.await()
        val (maxFeePerGas, inclusionFeePerGas) = feeDeferred.await()

        listOf(
            EthereumFee(
                feeLevel = FeeLevel.Low,
                gas = estimateGas,
                maxFeePerGas = maxFeePerGas,
                priorityFeeGas = inclusionFeePerGas
            ),
            EthereumFee(
                feeLevel = FeeLevel.Average,
                gas = estimateGas,
                maxFeePerGas = maxFeePerGas,
                priorityFeeGas = inclusionFeePerGas
            ),
            EthereumFee(
                feeLevel = FeeLevel.Fast,
                gas = estimateGas,
                maxFeePerGas = maxFeePerGas,
                priorityFeeGas = inclusionFeePerGas
            )
        )
    }

    override suspend fun signAndBroadcast(
        coin: AssetCoin,
        toAddress: String,
        amount: String,
        fee: FeeModel
    ): String = withContext(Dispatchers.Default) {
        if (fee !is EthereumFee) throw IllegalArgumentException("fee model is incorrect")
        val isEthNormalTransfer = coin.contract.isNullOrBlank()

        val nonceDeferred = async { fetchingNonce(coin.platform, coin.address) }
        val nonce = nonceDeferred.await()

        val maxFeePerGas = fee.maxFeePerGas
        val inclusionFeePerGas = fee.priorityFeeGas
        val gasLimit = fee.gas

        // double check balance if enough to send for fee
        val chainId = coin.platform.chainIdentifier ?: "0x01"
        val signingInput = SigningInput(
            private_key = ByteString.of(*coin.privateKey),
            to_address = coin.contract.ifNullOrBlank { toAddress },
            tx_mode = TransactionMode.Enveloped,
            chain_id = chainId.asHex(),
            nonce = nonce.asHex(),
            max_fee_per_gas = maxFeePerGas.asHex(),
            max_inclusion_fee_per_gas = inclusionFeePerGas.asHex(),
            gas_limit = gasLimit.asHex(),
            transaction = if (isEthNormalTransfer) {
                Transaction(
                    transfer = Transaction.Transfer(
                        amount = amount.asHex()
                    )
                )
            } else Transaction(
                erc20_transfer = Transaction.ERC20Transfer(
                    to = toAddress,
                    amount = amount.asHex()
                )
            )
        )
        val output = AnySigner.sign(signingInput, CoinType.Ethereum, SigningOutput.ADAPTER)
        val encoded = "0x${output.encoded.hex()}"
        jsonRpcApi.sendRawTransaction(coin.platform.network!!.rpcUrl, encoded)
    }

    private suspend fun estimateGas(
        platform: AssetPlatform,
        account: String,
        toAddress: String,
        contractAddress: String?,
        amount: String
    ): String {
        val rpcUrl = platform.network!!.rpcUrl
        val data = if (contractAddress.isNullOrBlank()) {
            ""
        } else {
            val amountUInt = (ZERO_UINT + amount.cleanHexPrefix()).takeLast(64)
            "0xa9059cbb000000000000000000000000${toAddress.cleanHexPrefix()}$amountUInt"
        }
        val estimateGas = jsonRpcApi.estimateGas(
            rpcUrl = rpcUrl,
            from = account,
            to = contractAddress.ifNullOrBlank { toAddress },
            value = null,
            data = data
        )
        return estimateGas
    }

    private suspend fun calculateFee(platform: AssetPlatform): Pair<String, String> {
        val rpcUrl = platform.network!!.rpcUrl
        return jsonRpcApi.feeHistory(rpcUrl = rpcUrl, 5, listOf(20, 30))
    }

    private suspend fun fetchingNonce(platform: AssetPlatform, account: String): String {
        val rpcUrl = platform.network!!.rpcUrl
        return jsonRpcApi.getTransactionCount(rpcUrl = rpcUrl, account)
    }
}

private const val ZERO_UINT = "0000000000000000000000000000000000000000000000000000000000000000"
