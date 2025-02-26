package com.surgery.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class ChannelDto(
    val banner: BannerDto?
)