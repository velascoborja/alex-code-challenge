package com.surgery.view_model.base

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface ViewState
interface ViewEvent
interface ViewEffect

abstract class BaseViewModel<UiState : ViewState, Event : ViewEvent, Effect : ViewEffect>(
    initialState: UiState,
) : ViewModel() {

    abstract fun handleEvent(event: Event)

    private val _viewState: MutableStateFlow<UiState> = MutableStateFlow(initialState)
    val viewState: StateFlow<UiState>
        get() = _viewState.asStateFlow()

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()

    private val _effects: Channel<Effect> = Channel()
    val effect: Flow<Effect>
        get() = _effects.receiveAsFlow()

    init {
        subscribeToEvents()
    }

    private fun subscribeToEvents() {
        viewModelScope.launch {
            _event.collectLatest {
                handleEvent(it)
            }
        }
    }

    fun setEvent(e: Event) {
        viewModelScope.launch { _event.emit(e) }
    }

    protected fun setState(reducer: UiState.() -> UiState) {
        val newState = viewState.value.reducer()
        _viewState.update { newState }
    }


    @VisibleForTesting
    fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch { _effects.send(effectValue) }
    }
}