package com.easy.wallet.model.news

import kotlin.native.ObjCName

/**
 * "title": "Binance's Asia-Pacific Head Departs Amidst Market Share Decline and Regulatory Pressures",
 * "source": "cryptonews.com",
 * "language": "en",
 * "link": "https://cryptonews.com/news/binances-asia-pacific-head-departs-amidst-market-share-decline-and-regulatory-pressures.htm",
 * "link_amp": false,
 * "link_iframable": true,
 * "time": "2023-09-01 00:59:00",
 * "tags": "Blockchain News, Binance, Asia, China",
 * "description": "According to Bloomberg's sources, Leon Foong, who served as the head of Binance Asia-Pacific, is leaving his position at the world's largest cryptocurrency exchange.  Foong was crucial in Binance's growth in South Korea, Thailand, and Japan. While an official announcement is pending, insiders have signaled his imminent departure.... Read More: Binance's Asia-Pacific Head Departs Amidst Market Share Decline and Regulatory Pressures",
 * "hash": "0063723b0b",
 * "file": "binances-asiapacific-head-departs-amidst-market-share-decline-and-regulatory-pressures",
 * "permalink": "https://blockchair.com/news/binances-asiapacific-head-departs-amidst-market-share-decline-and-regulatory-pressures--0063723b0b"
 */
@ObjCName("News")
data class News(
    val title: String,
    val source: String,
    val language: String,
    val link: String,
    val time: String,
    val hash: String,
    val description: String,
    val tags: String
)
