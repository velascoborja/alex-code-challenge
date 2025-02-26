package com.surgery.data.local

import com.surgery.db.PhaseEntity
import com.surgery.db.ProcedureEntity
import kotlinx.coroutines.flow.Flow

internal interface Database {
    suspend fun saveProcedures(procedureEntities: List<ProcedureEntity>)
    suspend fun savePhases(procedureId: String, phaseEntities: List<PhaseEntity>)
    fun getProcedures(search: String): Flow<List<ProcedureEntity>>
    fun getFavouriteProcedures(search: String): Flow<List<ProcedureEntity>>
    suspend fun getPhases(procedureId: String): List<PhaseEntity>
    suspend fun updateProcedureFavouriteState(procedureId: String, isFavourite: Boolean)
}