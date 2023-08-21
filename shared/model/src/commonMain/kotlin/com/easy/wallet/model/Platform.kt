package com.easy.wallet.model

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform