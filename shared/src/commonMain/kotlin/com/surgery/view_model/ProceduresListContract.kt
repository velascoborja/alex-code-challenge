package com.surgery.view_model

import com.surgery.domain.model.Procedure
import com.surgery.view_model.base.ViewEffect
import com.surgery.view_model.base.ViewEvent
import com.surgery.view_model.base.ViewState

class ProceduresListContract {
    data class State(
        val proceduresList: List<Procedure> = emptyList(),
        val proceduresFavouriteList: List<Procedure> = emptyList(),
        val searchText: String = "",
        val searchTextFavourite: String = "",
        val error: Exception? = null,
    ) : ViewState {
        companion object {
            fun initial() = State(
                proceduresList = emptyList(),
                proceduresFavouriteList = emptyList(),
            )
        }
    }

    sealed class Event : ViewEvent {
        data object RefreshProceduresList : Event()
        data class LoadProcedures(val text: String = "") : Event()
        data class LoadFavouriteProcedures(val text: String = "") : Event()
        data class ToggleFavourite(val procedure: Procedure) : Event()
        data class OpenProceduresDetailsView(val procedure: Procedure) : Event()
    }

    sealed class Effect : ViewEffect {
        data class Loading(val isLoading: Boolean) : Effect()
        data class ShowError(val e: Throwable) : Effect()

        sealed class Navigation : Effect() {
            data class ToProceduresDetailsScreen(val procedure: Procedure) :
                Navigation()
        }
    }
}