package com.surgery.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PhaseDto(
    @SerialName("deep_link") val deepLink: String,
    val icon: IconDto,
    @SerialName("learn_completed") val learnCompleted: Boolean,
    val name: String,
    @SerialName("test_mode") val testMode: Boolean,
    val uuid: String,
    val viewed: Boolean
)