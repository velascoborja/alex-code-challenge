package com.surgery.domain.usecase

import com.surgery.domain.model.Procedure
import com.surgery.domain.repository.ProcedureRepository

class GetProcedureDetailsUseCase(private val procedureRepository: ProcedureRepository) {
    suspend operator fun invoke(procedure: Procedure) =
        procedureRepository.getProcedureDetails(procedure)
}