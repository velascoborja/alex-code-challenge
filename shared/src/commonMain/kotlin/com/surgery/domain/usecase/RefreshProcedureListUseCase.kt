package com.surgery.domain.usecase

import com.surgery.domain.repository.ProcedureRepository

class RefreshProcedureListUseCase(private val procedureRepository: ProcedureRepository) {
    suspend operator fun invoke() = procedureRepository.refreshProcedureList()
}