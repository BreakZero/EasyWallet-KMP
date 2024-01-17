package com.easy.wallet.model.data

data class EthereumTransactionUiModel(
    override val hash: String,
    override val amount: String,
    override val recipient: String,
    override val sender: String,
    override val direction: Direction,
    val gas: String,
    val gasPrice: String,
    val gasUsed: String
) : Transaction
