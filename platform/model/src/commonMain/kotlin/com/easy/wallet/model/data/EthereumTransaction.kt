package com.easy.wallet.model.data

data class EthereumTransaction(
    override val hash: String,
    override val amount: String,
    override val recipient: String,
    override val sender: String
) : Transaction
