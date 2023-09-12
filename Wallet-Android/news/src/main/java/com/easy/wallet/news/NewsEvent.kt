package com.easy.wallet.news

internal sealed interface NewsEvent {
    data class ClickItem(val url: String): NewsEvent
    data object CloseNews: NewsEvent
}