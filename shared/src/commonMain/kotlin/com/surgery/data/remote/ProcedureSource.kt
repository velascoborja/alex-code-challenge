package com.surgery.data.remote

import com.surgery.data.model.ProcedureDetailsDto
import com.surgery.data.model.ProcedureDto

internal interface ProcedureSource {
    suspend fun getProcedureList(): Result<List<ProcedureDto>>
    suspend fun getProcedureDetails(procedureId: String): Result<ProcedureDetailsDto>
}