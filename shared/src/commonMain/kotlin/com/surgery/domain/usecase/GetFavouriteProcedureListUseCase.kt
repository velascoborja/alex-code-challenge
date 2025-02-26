package com.surgery.domain.usecase

import com.surgery.domain.repository.ProcedureRepository

class GetFavouriteProcedureListUseCase(private val procedureRepository: ProcedureRepository) {
    suspend operator fun invoke(search: String) =
        procedureRepository.getFavouriteProcedureList(search)
}