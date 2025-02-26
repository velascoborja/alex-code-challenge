package com.surgery.domain.usecase

import com.surgery.domain.model.Phase
import com.surgery.domain.model.Procedure
import com.surgery.domain.repository.ProcedureRepository
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDateTime
import kotlin.test.Test

class ToggleProcedureFavoriteUseCaseTest {

    private val procedureRepository: ProcedureRepository = mock()
    private val toggleProcedureFavoriteUseCase = ToggleProcedureFavoriteUseCase(procedureRepository)

    @Test
    fun testInvoke() = runTest {
        everySuspend { procedureRepository.toggleProcedureFavouriteState(procedure) } returns Unit

        toggleProcedureFavoriteUseCase(procedure)

        verifySuspend(VerifyMode.exhaustiveOrder) {
            procedureRepository.toggleProcedureFavouriteState(procedure)
        }
    }

    private companion object {
        val phase = Phase(
            icon = "icon",
            name = "name",
            uuid = "uuis",
        )
        val procedure = Procedure(
            uuid = "1",
            name = "Procedure 1",
            creationDate = LocalDateTime.parse("2015-04-14T10:00:51.940581"),
            duration = 60,
            icon = "icon1",
            phasesCount = 1,
            isFavorite = true,
            phases = listOf(phase)
        )
    }
}