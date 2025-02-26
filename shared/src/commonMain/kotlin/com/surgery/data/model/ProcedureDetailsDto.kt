package com.surgery.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ProcedureDetailsDto(
    val author: String?,
    val card: CardDto,
    val channel: ChannelDto,
    @SerialName("date_published") val datePublished: String,
    @SerialName("deep_link") val deepLink: String,
    @SerialName("doi_code") val doiCode: String?,
    val duration: Int,
    val icon: IconDto,
    @SerialName("is_purchasable") val isPurchasable: Boolean?,
    val name: String,
    val organisation: String?,
    val overview: List<Int>,
    val phases: List<PhaseDto>,
    @SerialName("specialties") val specialties: List<String>,
    val uuid: String,
    @SerialName("view_count") val viewCount: Int,
)