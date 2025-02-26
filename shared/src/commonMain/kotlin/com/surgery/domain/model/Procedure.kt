package com.surgery.domain.model

import kotlinx.datetime.LocalDateTime

data class Procedure(
    val creationDate: LocalDateTime,
    val duration: Int,
    val icon: String,
    val name: String,
    val phasesCount: Int,
    var phases: List<Phase>?,
    val uuid: String,
    var isFavorite: Boolean,
)