package com.easy.wallet.model.data

enum class Direction {
    SEND, RECEIVE
}

interface Transaction {
    val hash: String
    val amount: String
    val recipient: String
    val sender: String
    val direction: Direction
}
