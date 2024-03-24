package com.easy.wallet.shared.data.repository.transactions

import com.easy.wallet.model.TokenInformation
import com.easy.wallet.model.data.Direction
import com.easy.wallet.model.data.EthereumTransactionUiModel
import com.easy.wallet.model.data.Transaction
import com.easy.wallet.network.source.etherscan.EtherscanApi
import com.easy.wallet.network.source.etherscan.dto.EtherTransactionDto
import com.ionspin.kotlin.bignum.decimal.RoundingMode
import com.ionspin.kotlin.bignum.decimal.toBigDecimal

internal fun EtherTransactionDto.asTransactionUiModel(
    token: TokenInformation,
    account: String
): EthereumTransactionUiModel {
    val direction = if (from.equals(account, true)) Direction.SEND else Direction.RECEIVE
    return EthereumTransactionUiModel(
        hash = hash,
        amount = value.toBigDecimal().moveDecimalPoint(-token.decimals)
            .roundToDigitPositionAfterDecimalPoint(8, RoundingMode.ROUND_HALF_CEILING)
            .toPlainString(),
        recipient = to,
        sender = from,
        direction = direction,
        gasPrice = gasPrice,
        gas = gas,
        gasUsed = gasUsed,
        symbol = token.symbol,
        functionName = functionName
    )
}

class EthereumTransactionRepository internal constructor(
    private val etherscanApi: EtherscanApi
) : TransactionRepository {
    override suspend fun getTransactions(
        token: TokenInformation,
        page: Int,
        offset: Int,
        account: String
    ): List<Transaction> {
        val tnxDto = etherscanApi.getTransactions(page, offset, account)
        return tnxDto.map { it.asTransactionUiModel(token, account) }
    }

    override suspend fun getContractInternalTransactions(
        token: TokenInformation,
        page: Int,
        offset: Int,
        account: String
    ): List<Transaction> {
        val tnxDto = etherscanApi.getContractInternalTransactions(page, offset, account, token.contract.orEmpty())
        return tnxDto.map { it.asTransactionUiModel(token, account) }
    }
}
