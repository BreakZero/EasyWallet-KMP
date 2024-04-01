package com.easy.wallet.shared.model.transaction

enum class Direction {
    SEND, RECEIVE
}
enum class TransactionStatus {
    Confirmed, Pending, Failed
}

sealed class TransactionUiModel {
    abstract val hash: String
    abstract val amount: String
    abstract val recipient: String
    abstract val symbol: String
    abstract val sender: String
    abstract val direction: Direction
    abstract val datetime: String
    abstract val status: TransactionStatus
}
