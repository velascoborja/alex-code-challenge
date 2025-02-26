package com.surgery.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.surgery.composable.FullScreenError
import com.surgery.domain.model.Procedure
import com.surgery.procedures_list.ProceduresListScreen
import com.surgery.procedures_list.comp.ProcedureDetailBottomSheet
import com.surgery.procedures_list.comp.TwoTabsScreen
import com.surgery.state.FullScreenLoadingManager
import com.surgery.view_model.ProceduresListContract
import com.surgery.view_model.ProceduresListViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object ProceduresList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProceduresListDestination() {
    val viewModel: ProceduresListViewModel = koinViewModel()
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    var selectedProcedure by remember { mutableStateOf<Procedure?>(null) }

    LaunchedEffect(Unit) {
        viewModel.setEvent(ProceduresListContract.Event.RefreshProceduresList)
        viewModel.setEvent(ProceduresListContract.Event.LoadProcedures())
        viewModel.setEvent(ProceduresListContract.Event.LoadFavouriteProcedures())

        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is ProceduresListContract.Effect.Navigation.ToProceduresDetailsScreen -> {
                    selectedProcedure = effect.procedure
                }

                is ProceduresListContract.Effect.Loading -> {
                    if (effect.isLoading) {
                        FullScreenLoadingManager.showLoader()
                    } else {
                        FullScreenLoadingManager.hideLoader()
                    }
                }

                is ProceduresListContract.Effect.ShowError -> {
                    FullScreenLoadingManager.showError(
                        effect.e.message.toString()
                    )
                }
            }
        }
    }
    FullScreenError(
        onRetryClick = {
            viewModel.setEvent(ProceduresListContract.Event.RefreshProceduresList)
        }
    )

    TwoTabsScreen(
        contentAll = {
            ProceduresListScreen(
                searchText = state.searchText,
                proceduresList = state.proceduresList,
                onSearchUpdated = { search ->
                    viewModel.setEvent(ProceduresListContract.Event.LoadProcedures(search))
                },
                onItemClick = { procedure ->
                    viewModel.setEvent(
                        ProceduresListContract.Event.OpenProceduresDetailsView(
                            procedure
                        )
                    )
                },
                onToggleFavorite = { procedure ->
                    viewModel.setEvent(ProceduresListContract.Event.ToggleFavourite(procedure))
                }
            )
        },
        contentFavourite = {
            ProceduresListScreen(
                searchText = state.searchTextFavourite,
                proceduresList = state.proceduresFavouriteList,
                onSearchUpdated = { search ->
                    viewModel.setEvent(ProceduresListContract.Event.LoadProcedures(search))
                },
                onItemClick = { procedure ->
                    viewModel.setEvent(
                        ProceduresListContract.Event.OpenProceduresDetailsView(
                            procedure
                        )
                    )
                },
                onToggleFavorite = { procedure ->
                    viewModel.setEvent(ProceduresListContract.Event.ToggleFavourite(procedure))
                }
            )
        }
    )

    selectedProcedure?.let {
        ModalBottomSheet(
            onDismissRequest = {
                selectedProcedure = null
            }
        ) {
            ProcedureDetailBottomSheet(
                modifier = Modifier,
                procedure = it,
                onToggleFavorite = { procedure ->
                    viewModel.setEvent(ProceduresListContract.Event.ToggleFavourite(procedure))
                },
                onDismiss = {
                    selectedProcedure = null
                },
            )
        }
    }
}