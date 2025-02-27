package com.surgery.data.mapper

import com.surgery.data.model.PhaseDto
import com.surgery.data.model.ProcedureDto
import com.surgery.db.PhaseEntity
import com.surgery.db.ProcedureEntity
import com.surgery.domain.model.Phase
import com.surgery.domain.model.Procedure
import kotlinx.datetime.LocalDateTime

internal fun ProcedureDto.toEntity() = ProcedureEntity(
    uuid = uuid,
    name = name,
    icon = icon.url,
    phasesCount = phases.size,
    duration = duration,
    creationDate = datePublished,
    isFavorite = false,
)

internal fun PhaseDto.toEntity(procedureUuid: String) = PhaseEntity(
    uuid = uuid,
    procedureUuid = procedureUuid,
    name = name,
    icon = icon.url,
)

internal fun ProcedureEntity.toDomain() = Procedure(
    uuid = uuid,
    name = name,
    creationDate = LocalDateTime.parse(creationDate),
    duration = duration.toInt(),
    icon = icon,
    phasesCount = phasesCount.toInt(),
    isFavorite = isFavorite,
    phases = null,
)

internal fun PhaseEntity.toDomain() = Phase(
    uuid = uuid,
    name = name,
    icon = icon,
)

internal fun Procedure.toEntity() = ProcedureEntity(
    uuid = uuid,
    name = name,
    creationDate = creationDate.toString(),
    duration = duration,
    icon = icon,
    phasesCount = phasesCount,
    isFavorite = isFavorite,
)