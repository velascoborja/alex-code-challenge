package com.surgery.view_model

import app.cash.turbine.test
import com.surgery.domain.model.Phase
import com.surgery.domain.model.Procedure
import com.surgery.domain.repository.ProcedureRepository
import com.surgery.domain.usecase.GetFavouriteProcedureListUseCase
import com.surgery.domain.usecase.GetProcedureDetailsUseCase
import com.surgery.domain.usecase.GetProcedureListUseCase
import com.surgery.domain.usecase.RefreshProcedureListUseCase
import com.surgery.domain.usecase.ToggleProcedureFavoriteUseCase
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode
import dev.mokkery.verifySuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDateTime
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class ProceduresListViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: ProceduresListViewModel
    private val procedureRepository: ProcedureRepository = mock()
    private val getProcedureListUseCase: GetProcedureListUseCase =
        GetProcedureListUseCase(procedureRepository)
    private val getProcedureDetailsUseCase: GetProcedureDetailsUseCase =
        GetProcedureDetailsUseCase(procedureRepository)
    private val toggleProcedureFavoriteUseCase: ToggleProcedureFavoriteUseCase =
        ToggleProcedureFavoriteUseCase(procedureRepository)
    private val refreshProcedureListUseCase: RefreshProcedureListUseCase =
        RefreshProcedureListUseCase(procedureRepository)
    private val getFavouriteProcedureListUseCase: GetFavouriteProcedureListUseCase =
        GetFavouriteProcedureListUseCase(procedureRepository)

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ProceduresListViewModel(
            getProcedureListUseCase = getProcedureListUseCase,
            getProcedureDetailsUseCase = getProcedureDetailsUseCase,
            toggleProcedureFavoriteUseCase = toggleProcedureFavoriteUseCase,
            refreshProcedureListUseCase = refreshProcedureListUseCase,
            getFavouriteProcedureListUseCase = getFavouriteProcedureListUseCase,

            )
    }

    @AfterTest
    fun cleanUp() {
        Dispatchers.resetMain()
    }

    @Test
    fun `refreshProceduresList should call refreshProcedureListUseCase and emit loading effects`() =
        runTest {
            everySuspend { procedureRepository.refreshProcedureList() } returns Unit

            viewModel.setEvent(ProceduresListContract.Event.RefreshProceduresList)

            viewModel.viewState.test {
                assertEquals(ProceduresListContract.State.initial(), awaitItem())
            }

            viewModel.effect.test {
                assertEquals(ProceduresListContract.Effect.Loading(true), awaitItem())
                verifySuspend(VerifyMode.exhaustiveOrder) {
                    procedureRepository.refreshProcedureList()
                }
                assertEquals(ProceduresListContract.Effect.Loading(false), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `loadProcedures with search should call getProcedureListUseCase and update state`() =
        runTest {
            everySuspend { procedureRepository.getProcedureList("test") } returns flowOf(
                listOf(procedure)
            )

            viewModel.viewState.test {
                viewModel.setEvent(ProceduresListContract.Event.LoadProcedures("test"))
                assertEquals(
                    ProceduresListContract.State(), awaitItem()
                )
                assertEquals(
                    ProceduresListContract.State(searchText = "test"), awaitItem()
                )
                verifySuspend(VerifyMode.exhaustiveOrder) {
                    procedureRepository.getProcedureList("test")
                }
                assertEquals(
                    ProceduresListContract.State(
                        proceduresList = listOf(procedure),
                        searchText = "test",
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `loadProcedures should call getProcedureListUseCase and update state`() = runTest {
        everySuspend { procedureRepository.getProcedureList() } returns flowOf(
            listOf(procedure)
        )

        viewModel.viewState.test {
            viewModel.setEvent(ProceduresListContract.Event.LoadProcedures())
            assertEquals(
                ProceduresListContract.State(
                    proceduresList = emptyList(),
                    searchText = "",
                    error = null,
                ), awaitItem()
            )

            assertEquals(
                ProceduresListContract.State(
                    proceduresList = listOf(procedure),
                    searchText = "",
                    error = null,
                ), awaitItem()
            )

            verifySuspend(VerifyMode.exhaustiveOrder) {
                procedureRepository.getProcedureList()
            }

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadProcedureDetails should call getProcedureDetailsUseCase when phases are empty and emit effects`() =
        runTest {

            val procedureRequest = procedure.copy(phases = null)
            everySuspend { procedureRepository.getProcedureDetails(procedureRequest) } returns Result.success(
                listOf(phase)
            )

            viewModel.effect.test {
                viewModel.setEvent(
                    ProceduresListContract.Event.OpenProceduresDetailsView(
                        procedureRequest
                    )
                )
                assertEquals(ProceduresListContract.Effect.Loading(true), awaitItem())
                verifySuspend(VerifyMode.exhaustiveOrder) {
                    procedureRepository.getProcedureDetails(procedureRequest)
                }
                assertEquals(
                    ProceduresListContract.Effect.Navigation.ToProceduresDetailsScreen(
                        procedureRequest.copy(phases = listOf(phase))
                    ), awaitItem()
                )
                assertEquals(ProceduresListContract.Effect.Loading(false), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `loadProcedureDetails should not call getProcedureDetailsUseCase when phases are not empty and emit effects`() =
        runTest {
            viewModel.effect.test {
                viewModel.setEvent(
                    ProceduresListContract.Event.OpenProceduresDetailsView(procedure)
                )
                assertEquals(
                    ProceduresListContract.Effect.Navigation.ToProceduresDetailsScreen(
                        procedure
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }

    private companion object {
        val phase = Phase(
            icon = "icon",
            name = "name",
            uuid = "uuid",
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