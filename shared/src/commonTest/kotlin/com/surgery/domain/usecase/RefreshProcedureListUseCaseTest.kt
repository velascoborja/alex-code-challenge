package com.surgery.domain.usecase

import com.surgery.domain.repository.ProcedureRepository
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class RefreshProcedureListUseCaseTest {

    private val procedureRepository: ProcedureRepository = mock()
    private val refreshProcedureListUseCase = RefreshProcedureListUseCase(procedureRepository)

    @Test
    fun testInvoke() = runTest {
        everySuspend { procedureRepository.refreshProcedureList() } returns Unit

        refreshProcedureListUseCase()

        verifySuspend(VerifyMode.exhaustiveOrder) { procedureRepository.refreshProcedureList() }
    }
}