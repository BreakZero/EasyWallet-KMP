package com.easy.wallet.model.data

interface Transaction {
    val hash: String
    val amount: String
    val recipient: String
    val sender: String
}
