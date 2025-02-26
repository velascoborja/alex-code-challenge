package com.surgery.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ProcedureDto(
    val author: String?,
    @SerialName("date_published") val datePublished: String,
    @SerialName("deep_link") val deepLink: String,
    @SerialName("doi_code") val doiCode: String?,
    val duration: Int,
    val icon: IconDto,
    @SerialName("is_purchasable") val isPurchasable: Boolean?,
    val labels: List<String>,
    val name: String,
    val organisation: String?,
    val phases: List<String>,
    @SerialName("site_slug") val siteSlug: String,
    val specialties: List<String>,
    val uuid: String,
)