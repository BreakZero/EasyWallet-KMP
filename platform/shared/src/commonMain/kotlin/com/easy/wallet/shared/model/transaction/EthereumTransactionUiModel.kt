package com.easy.wallet.shared.model.transaction

data class EthereumTransactionUiModel(
    override val hash: String,
    override val amount: String,
    override val recipient: String,
    override val sender: String,
    override val direction: Direction,
    override val symbol: String,
    val gas: String,
    val gasPrice: String,
    val gasUsed: String,
    val functionName: String?
) : TransactionUiModel()
