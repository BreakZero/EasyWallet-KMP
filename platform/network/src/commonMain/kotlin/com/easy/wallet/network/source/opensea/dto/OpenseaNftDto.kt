package com.easy.wallet.network.source.opensea.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class NftListDto(
  @SerialName("nfts")
  val nfts: List<OpenSeaNftDto>
)

@Serializable
internal data class OpenSeaNftDto(
  @SerialName("collection")
  val collection: String,
  @SerialName("contract")
  val contract: String,
  @SerialName("created_at")
  val createdAt: String,
  @SerialName("description")
  val description: String? = null,
  @SerialName("identifier")
  val identifier: String,
  @SerialName("image_url")
  val imageUrl: String,
  @SerialName("is_disabled")
  val isDisabled: Boolean,
  @SerialName("is_nsfw")
  val isNsfw: Boolean,
  @SerialName("metadata_url")
  val metadataUrl: String? = null,
  @SerialName("name")
  val name: String,
  @SerialName("token_standard")
  val tokenStandard: String,
  @SerialName("updated_at")
  val updatedAt: String
)
