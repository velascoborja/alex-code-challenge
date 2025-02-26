package com.surgery.domain.repository

import com.surgery.domain.model.Phase
import com.surgery.domain.model.Procedure
import kotlinx.coroutines.flow.Flow

interface ProcedureRepository {
    suspend fun getProcedureList(search: String = ""): Flow<List<Procedure>>
    suspend fun getFavouriteProcedureList(search: String = ""): Flow<List<Procedure>>
    suspend fun refreshProcedureList()
    suspend fun getProcedureDetails(procedure: Procedure): Result<List<Phase>>
    suspend fun toggleProcedureFavouriteState(procedure: Procedure)
}