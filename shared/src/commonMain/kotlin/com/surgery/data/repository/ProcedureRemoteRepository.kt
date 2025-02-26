package com.surgery.data.repository

import com.surgery.data.local.Database
import com.surgery.data.mapper.toDomain
import com.surgery.data.mapper.toEntity
import com.surgery.data.remote.ProcedureSource
import com.surgery.domain.model.Procedure
import com.surgery.domain.repository.ProcedureRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ProcedureRemoteRepository(
    private val procedureSource: ProcedureSource,
    private val database: Database,
) : ProcedureRepository {
    override suspend fun getProcedureList(search: String): Flow<List<Procedure>> {
        return database.getProcedures(search).map { list -> list.map { it.toDomain() } }
    }

    override suspend fun refreshProcedureList() {
        procedureSource.getProcedureList()
            .onSuccess { resultDto ->
                val procedureEntities = resultDto.map { it.toEntity() }
                database.saveProcedures(procedureEntities)
            }
            .onFailure {
                println("Can not refresh procedure list: ${it.message}")
            }
    }

    override suspend fun getProcedureDetails(procedure: Procedure) =
        procedureSource.getProcedureDetails(procedure.uuid)
            .fold(
                onSuccess = { resultDto ->
                    val phaseEntities =
                        resultDto.phases.map { phase -> phase.toEntity(resultDto.uuid) }
                    database.savePhases(procedure.uuid, phaseEntities)
                    Result.success(phaseEntities.map { it.toDomain() })
                },
                onFailure = {
                    val cachedProcedureEntities = database.getPhases(procedure.uuid)
                        .map { phaseEntity -> phaseEntity.toDomain() }
                    if (cachedProcedureEntities.isEmpty()) {
                        Result.failure(it)
                    } else {
                        Result.success(cachedProcedureEntities)
                    }
                }
            )

    override suspend fun toggleProcedureFavouriteState(procedure: Procedure) {
        database.updateProcedureFavouriteState(
            procedureId = procedure.uuid,
            isFavourite = !procedure.isFavorite,
        )
    }

    override suspend fun getFavouriteProcedureList(search: String): Flow<List<Procedure>> {
        return database.getFavouriteProcedures(search).map { list -> list.map { it.toDomain() } }
    }
}