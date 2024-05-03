package com.easy.wallet.shared.domain

import com.easy.wallet.core.commom.Constants
import com.easy.wallet.model.TokenBasicResult
import com.easy.wallet.shared.data.multiwallet.MultiWalletRepository
import com.easy.wallet.shared.model.fees.FeeModel
import com.ionspin.kotlin.bignum.decimal.toBigDecimal
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.trustwallet.core.CoinType
import com.trustwallet.core.HDWallet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class TransactionSigningUseCase internal constructor(
    private val walletRepository: MultiWalletRepository,
    private val basicInfoUseCase: GetToKenBasicInfoUseCase,
    private val exactTokenRepositoryUseCase: GetExactTokenRepositoryUseCase
) {

    @NativeCoroutines
    operator fun invoke(
        tokenId: String,
        chainId: String,
        toAddress: String,
        amount: String,
        fee: FeeModel
    ): Flow<String> {
        return basicInfoUseCase(tokenId).map { basicInfo ->
            // active wallet flow never stop, we need the first one when signing is OK
            val wallet = walletRepository.forActivatedOne().filterNotNull().first()

            val hdWallet = HDWallet(wallet.mnemonic, wallet.passphrase)
            val privateKey = getPrivateKeyFromChain(hdWallet, basicInfo)
            exactTokenRepositoryUseCase(basicInfo).signAndBroadcast(
                account = basicInfo.address,
                chainId = chainId,
                privateKey = privateKey,
                contractAddress = basicInfo.contract,
                toAddress = toAddress,
                amount = amount.toBigDecimal().moveDecimalPoint(basicInfo.decimals).toBigInteger()
                    .toString(16),
                fee = fee
            )
        }
    }

    private fun getPrivateKeyFromChain(
        hdWallet: HDWallet,
        basicResult: TokenBasicResult
    ): ByteArray {
        val coinType = when (basicResult.chainName) {
            Constants.ETH_CHAIN_NAME -> CoinType.Ethereum
            else -> throw NotImplementedError("The chain(${basicResult.chainName}) not supported yet.")
        }
        return hdWallet.getKeyForCoin(coinType).data
    }
}
