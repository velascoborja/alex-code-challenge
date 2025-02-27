package com.surgery.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.surgery.db.PhaseEntity
import com.surgery.db.ProcedureEntity
import com.surgery.db.SurgeryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

internal class SqlDelightDatabase(
    private val database: SurgeryDatabase,
    private val context: CoroutineContext = Dispatchers.Default,
) : Database {

    override suspend fun saveProcedures(procedureEntities: List<ProcedureEntity>) =
        withContext(context) {
            val favoriteUuid = mutableListOf<String>()
            database.transaction {
                val favoriteProcedures =
                    database.surgeryDatabaseQueries.selectFavoriteProcedureUuid().executeAsList()
                database.surgeryDatabaseQueries.deleteAllProcedures()
                procedureEntities.forEach {
                    saveProcedure(it)
                    if (favoriteProcedures.contains(it.uuid)) {
                        favoriteUuid.add(it.uuid)
                    }
                }
            }
            favoriteUuid.forEach {
                updateProcedureFavouriteState(procedureId = it, isFavourite = true)
            }
        }


    override fun getProcedures(search: String): Flow<List<ProcedureEntity>> {
        return (
                if (search.isEmpty())
                    database.surgeryDatabaseQueries.selectAllProcedures() else
                    database.surgeryDatabaseQueries.searchProcedures("%$search%")
                )
            .asFlow()
            .mapToList(context)
    }

    override fun getFavouriteProcedures(search: String): Flow<List<ProcedureEntity>> {
        return (
                if (search.isEmpty())
                    database.surgeryDatabaseQueries.selectFavoriteProcedure() else
                    database.surgeryDatabaseQueries.searchsFavoriteProcedure("%$search%")
                )
            .asFlow()
            .mapToList(context)
    }

    override suspend fun getPhases(procedureId: String): List<PhaseEntity> = withContext(context) {
        database.surgeryDatabaseQueries.selectPhasesForProcedure(procedureId).executeAsList()
    }

    override suspend fun savePhases(procedureId: String, phaseEntities: List<PhaseEntity>) =
        withContext(context) {
            database.transaction {
                database.surgeryDatabaseQueries.deletePhasesForProcedure(procedureId)
                phaseEntities.forEach {
                    savePhase(it)
                }
            }
        }

    override suspend fun updateProcedureFavouriteState(
        procedureId: String,
        isFavourite: Boolean
    ) = withContext(context) {
        database.transaction {
            database.surgeryDatabaseQueries.updateProcedureFavorite(
                isFavorite = isFavourite,
                uuid = procedureId,
            )
        }
    }

    private fun saveProcedure(procedure: ProcedureEntity) {
        database.surgeryDatabaseQueries.insertProcedure(
            uuid = procedure.uuid,
            name = procedure.name,
            icon = procedure.icon,
            phasesCount = procedure.phasesCount,
            duration = procedure.duration,
            creationDate = procedure.creationDate,
            isFavorite = procedure.isFavorite
        )
    }

    private fun savePhase(phase: PhaseEntity) {
        database.surgeryDatabaseQueries.insertPhase(
            uuid = phase.uuid,
            name = phase.name,
            icon = phase.icon,
            procedureUuid = phase.procedureUuid,
        )
    }
}