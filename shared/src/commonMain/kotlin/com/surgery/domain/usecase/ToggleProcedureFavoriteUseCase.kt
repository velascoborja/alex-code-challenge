package com.surgery.domain.usecase

import com.surgery.domain.model.Procedure
import com.surgery.domain.repository.ProcedureRepository

class ToggleProcedureFavoriteUseCase(private val procedureRepository: ProcedureRepository) {
    suspend operator fun invoke(procedure: Procedure) {
        procedureRepository.toggleProcedureFavouriteState(procedure)
    }
}