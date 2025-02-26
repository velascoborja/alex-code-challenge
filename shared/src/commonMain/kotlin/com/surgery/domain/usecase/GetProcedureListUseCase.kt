package com.surgery.domain.usecase

import com.surgery.domain.repository.ProcedureRepository

class GetProcedureListUseCase(private val procedureRepository: ProcedureRepository) {
    suspend operator fun invoke(search: String) = procedureRepository.getProcedureList(search)
}