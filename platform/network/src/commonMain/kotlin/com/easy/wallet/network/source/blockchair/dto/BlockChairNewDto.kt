package com.easy.wallet.network.source.blockchair.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class BlockChairNewsRootResponse(
  @SerialName("data")
  val data: List<BlockChairNewDto>?,
  @SerialName("context")
  val context: BlockChairContextDto
)

@Serializable
internal data class BlockChairNewDto(
  @SerialName("description")
  val description: String,
  @SerialName("file")
  val fileName: String,
  @SerialName("hash")
  val hash: String,
  @SerialName("language")
  val language: String,
  @SerialName("link")
  val link: String,
//    @SerialName("link_amp")
//    val linkAmp: Boolean,
//    @SerialName("link_iframable")
//    val linkIframable: Boolean?,
  @SerialName("permalink")
  val permalink: String,
  @SerialName("source")
  val source: String,
  @SerialName("tags")
  val tags: String,
  @SerialName("time")
  val time: String,
  @SerialName("title")
  val title: String
)
