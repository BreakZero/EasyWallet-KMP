package com.easy.wallet.data

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform