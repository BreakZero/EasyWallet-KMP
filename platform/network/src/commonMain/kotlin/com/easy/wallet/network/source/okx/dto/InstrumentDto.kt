package com.easy.wallet.network.source.okx.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class InstrumentDto(
    @SerialName("alias")
    val alias: String,
    @SerialName("baseCcy")
    val baseCcy: String,
    @SerialName("category")
    val category: String,
    @SerialName("ctMult")
    val ctMult: String,
    @SerialName("ctType")
    val ctType: String,
    @SerialName("ctVal")
    val ctVal: String,
    @SerialName("ctValCcy")
    val ctValCcy: String,
    @SerialName("expTime")
    val expTime: String,
    @SerialName("instFamily")
    val instFamily: String,
    @SerialName("instId")
    val instId: String,
    @SerialName("instType")
    val instType: String,
    @SerialName("lever")
    val lever: String,
    @SerialName("listTime")
    val listTime: String,
    @SerialName("lotSz")
    val lotSz: String,
    @SerialName("maxIcebergSz")
    val maxIcebergSz: String,
    @SerialName("maxLmtSz")
    val maxLmtSz: String,
    @SerialName("maxMktSz")
    val maxMktSz: String,
    @SerialName("maxStopSz")
    val maxStopSz: String,
    @SerialName("maxTriggerSz")
    val maxTriggerSz: String,
    @SerialName("maxTwapSz")
    val maxTwapSz: String,
    @SerialName("minSz")
    val minSz: String,
    @SerialName("optType")
    val optType: String,
    @SerialName("quoteCcy")
    val quoteCcy: String,
    @SerialName("settleCcy")
    val settleCcy: String,
    @SerialName("state")
    val state: String,
    @SerialName("stk")
    val stk: String,
    @SerialName("tickSz")
    val tickSz: String,
    @SerialName("uly")
    val uly: String
)
