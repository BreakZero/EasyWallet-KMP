package com.easy.wallet.core

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform