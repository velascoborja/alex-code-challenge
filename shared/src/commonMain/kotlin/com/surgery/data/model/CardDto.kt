package com.surgery.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class CardDto(
    val url: String,
    val uuid: String,
    val version: Int
)