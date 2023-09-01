package com.easy.wallet.data.news.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class BlockChairContextDto(
    @SerialName("api")
    val api: ApiDto = ApiDto(),
    @SerialName("cache")
    val cache: CacheDto = CacheDto(),
    @SerialName("code")
    val code: Int = 0,
    @SerialName("full_time")
    val fullTime: Double = 0.0,
    @SerialName("limit")
    val limit: Int = 0,
    @SerialName("offset")
    val offset: Int = 0,
    @SerialName("pre_rows")
    val preRows: Int = 0,
    @SerialName("render_time")
    val renderTime: Double = 0.0,
    @SerialName("request_cost")
    val requestCost: Double = 0.0,
    @SerialName("rows")
    val rows: Int = 0,
    @SerialName("servers")
    val servers: String = "",
    @SerialName("source")
    val source: String = "",
    @SerialName("time")
    val time: Double = 0.0,
    @SerialName("total_rows")
    val totalRows: Long? = null
) {
    @Serializable
    internal data class ApiDto(
        @SerialName("documentation")
        val documentation: String = "",
        @SerialName("last_major_update")
        val lastMajorUpdate: String = "",
        @SerialName("next_major_update")
        val nextMajorUpdate: Long? = null,
        @SerialName("notice")
        val notice: String = "",
        @SerialName("version")
        val version: String = ""
    )

    @Serializable
    internal data class CacheDto(
        @SerialName("duration")
        val duration: Int = 0,
        @SerialName("live")
        val live: Boolean = false,
        @SerialName("since")
        val since: String = "",
        @SerialName("time")
        val time: Double? = null,
        @SerialName("until")
        val until: String = ""
    )
}