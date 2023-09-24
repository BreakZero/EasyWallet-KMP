package com.easy.wallet.shared.data.repository.transactions

import com.easy.wallet.model.data.Direction
import com.easy.wallet.model.data.EthereumTransaction
import com.easy.wallet.model.data.Transaction
import com.easy.wallet.network.source.etherscan.EtherscanApi
import com.easy.wallet.network.source.etherscan.dto.EtherTransactionDto

internal fun EtherTransactionDto.asTransaction(account: String): EthereumTransaction {
    val direction = if (from.equals(account, true)) Direction.SEND else Direction.RECEIVE
    return EthereumTransaction(
        hash = hash,
        amount = value,
        recipient = to,
        sender = from,
        direction = direction,
        gasPrice = gasPrice,
        gas = gas,
        gasUsed = gasUsed,
    )
}

class EthereumTransactionRepository internal constructor(
    private val etherscanApi: EtherscanApi
) : TransactionRepository {
    override suspend fun getTransactions(
        page: Int,
        offset: Int,
        account: String
    ): List<Transaction> {
        val tnxDto = etherscanApi.getTransactions(page, offset, account)
        return tnxDto.map { it.asTransaction(account) }
    }
}
