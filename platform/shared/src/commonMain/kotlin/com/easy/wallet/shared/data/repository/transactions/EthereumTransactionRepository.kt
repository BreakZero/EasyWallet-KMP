package com.easy.wallet.shared.data.repository.transactions

import com.easy.wallet.model.data.EthereumTransaction
import com.easy.wallet.model.data.Transaction
import com.easy.wallet.network.source.etherscan.EtherscanApi
import com.easy.wallet.network.source.etherscan.dto.EtherTransactionDto

internal fun EtherTransactionDto.asTransaction(): EthereumTransaction {
    return EthereumTransaction(
        hash = hash,
        amount = value,
        recipient = to,
        sender = from,
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
        return tnxDto.map(EtherTransactionDto::asTransaction)
    }
}
