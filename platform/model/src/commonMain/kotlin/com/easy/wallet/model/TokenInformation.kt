package com.easy.wallet.model

data class TokenInformation(
    val id: String,
    val chainName: String,
    val name: String,
    val symbol: String,
    val decimal: Int,
    val contract: String?,
    val iconUri: String,
    val isActive: Boolean
)
