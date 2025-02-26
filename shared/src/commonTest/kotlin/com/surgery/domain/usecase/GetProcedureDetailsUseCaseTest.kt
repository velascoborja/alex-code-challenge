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
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetProcedureDetailsUseCaseTest {

    private val procedureRepository: ProcedureRepository = mock()
    private val getProcedureDetailsUseCase = GetProcedureDetailsUseCase(procedureRepository)

    @Test
    fun testInvokeSuccess() = runTest {
        val expectedDetails = Result.success(listOf(phase))

        everySuspend { procedureRepository.getProcedureDetails(procedure) } returns expectedDetails

        val result = getProcedureDetailsUseCase(procedure)

        assertTrue(result.isSuccess)
        assertEquals(expectedDetails, result)
        verifySuspend(VerifyMode.exhaustiveOrder) {
            procedureRepository.getProcedureDetails(
                procedure
            )
        }
    }

    @Test
    fun testInvokeFailure() = runTest {
        val exception = Exception("Network error")
        val expectedDetails = Result.failure<List<Phase>>(exception)

        everySuspend { procedureRepository.getProcedureDetails(procedure) } returns expectedDetails

        val result = getProcedureDetailsUseCase(procedure)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        verifySuspend(VerifyMode.exhaustiveOrder) {
            procedureRepository.getProcedureDetails(procedure)
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