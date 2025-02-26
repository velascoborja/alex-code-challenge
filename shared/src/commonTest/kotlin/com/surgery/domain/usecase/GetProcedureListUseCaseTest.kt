package com.surgery.domain.usecase

import com.surgery.domain.model.Phase
import com.surgery.domain.model.Procedure
import com.surgery.domain.repository.ProcedureRepository
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode
import dev.mokkery.verifySuspend
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals

class GetProcedureListUseCaseTest {

    private val procedureRepository: ProcedureRepository = mock()
    private val getProcedureListUseCase = GetProcedureListUseCase(procedureRepository)

    @Test
    fun testInvoke() = runTest {
        val searchQuery = "test"
        val expectedFlow: Flow<List<Procedure>> = flowOf(listOf(procedure))

        everySuspend { procedureRepository.getProcedureList(searchQuery) } returns expectedFlow

        val result = getProcedureListUseCase(searchQuery)

        assertEquals(expectedFlow, result)
        verifySuspend(VerifyMode.exhaustiveOrder) { procedureRepository.getProcedureList(searchQuery) }
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