package com.easy.wallet.domain

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform