package com.surgery.view_model

import androidx.lifecycle.viewModelScope
import com.surgery.domain.model.Procedure
import com.surgery.domain.usecase.GetFavouriteProcedureListUseCase
import com.surgery.domain.usecase.GetProcedureDetailsUseCase
import com.surgery.domain.usecase.GetProcedureListUseCase
import com.surgery.domain.usecase.RefreshProcedureListUseCase
import com.surgery.domain.usecase.ToggleProcedureFavoriteUseCase
import com.surgery.view_model.base.BaseViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class ProceduresListViewModel(
    private val getProcedureListUseCase: GetProcedureListUseCase,
    private val getProcedureDetailsUseCase: GetProcedureDetailsUseCase,
    private val toggleProcedureFavoriteUseCase: ToggleProcedureFavoriteUseCase,
    private val refreshProcedureListUseCase: RefreshProcedureListUseCase,
    private val getFavouriteProcedureListUseCase: GetFavouriteProcedureListUseCase,
) : BaseViewModel<ProceduresListContract.State, ProceduresListContract.Event, ProceduresListContract.Effect>(
    initialState = ProceduresListContract.State.initial(),
) {

    override fun handleEvent(event: ProceduresListContract.Event) {
        when (event) {
            is ProceduresListContract.Event.RefreshProceduresList -> refreshProceduresList()
            is ProceduresListContract.Event.OpenProceduresDetailsView -> loadProcedureDetails(event.procedure)
            is ProceduresListContract.Event.ToggleFavourite -> toggleProcedureFavorite(event.procedure)
            is ProceduresListContract.Event.LoadProcedures -> loadProcedures(
                search = event.text,
                debounce = 1.seconds,
            )

            is ProceduresListContract.Event.LoadFavouriteProcedures -> {
                loadFavouriteProcedures(
                    search = event.text,
                    debounce = 1.seconds,
                )
            }
        }
    }

    private fun refreshProceduresList() {
        viewModelScope.launch {
            setEffect {
                ProceduresListContract.Effect.Loading(true)
            }
            refreshProcedureListUseCase()
            setEffect {
                ProceduresListContract.Effect.Loading(false)
            }
        }
    }

    private fun loadProcedureDetails(procedure: Procedure) {
        viewModelScope.launch {
            if (procedure.phases.isNullOrEmpty()) {
                setEffect {
                    ProceduresListContract.Effect.Loading(true)
                }
                getProcedureDetailsUseCase(procedure).also { result ->
                    val phases = result.getOrElse {
                        setEffect {
                            ProceduresListContract.Effect.ShowError(it)
                        }
                        null
                    }

                    phases?.let {
                        procedure.phases = phases
                        setEffect {
                            ProceduresListContract.Effect.Navigation.ToProceduresDetailsScreen(
                                procedure
                            )
                        }
                    }
                }
                setEffect {
                    ProceduresListContract.Effect.Loading(false)
                }
            } else {
                setEffect {
                    ProceduresListContract.Effect.Navigation.ToProceduresDetailsScreen(
                        procedure
                    )
                }
            }
        }
    }

    private fun toggleProcedureFavorite(procedure: Procedure) {
        viewModelScope.launch {
            toggleProcedureFavoriteUseCase(procedure)
        }
    }

    @OptIn(FlowPreview::class)
    private fun loadProcedures(search: String = "", debounce: Duration = Duration.ZERO) {
        viewModelScope.launch {
            setState {
                copy(searchText = search)
            }
            getProcedureListUseCase(search)
                .debounce(debounce)
                .collect { procedures ->
                    setState {
                        copy(proceduresList = procedures)
                    }
                }
        }
    }

    @OptIn(FlowPreview::class)
    private fun loadFavouriteProcedures(search: String = "", debounce: Duration = Duration.ZERO) {
        viewModelScope.launch {
            setState {
                copy(searchTextFavourite = search)
            }
            getFavouriteProcedureListUseCase(search)
                .debounce(debounce)
                .collect { procedures ->
                    setState {
                        copy(proceduresFavouriteList = procedures)
                    }
                }
        }
    }
}