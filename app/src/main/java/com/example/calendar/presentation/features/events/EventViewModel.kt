package com.example.calendar.presentation.features.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calendar.domain.entity.Event
import com.example.calendar.domain.usecase.calendar.EventUsecaseInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UIState(
    val events: List<Event> = listOf()
)

@HiltViewModel
class EventViewModel @Inject constructor(private val usecase: EventUsecaseInterface) :
    EventViewModelInterface, ViewModel() {

    private val _uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState())
    override val uiState: StateFlow<UIState> = _uiState

    override fun addEvent(event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            usecase.addEvent(event)
        }
    }

    override fun getEvents(){
        viewModelScope.launch(Dispatchers.IO) {
            val events = usecase.getEvents()
            _uiState.update { it.copy(events = events) }
        }
    }



}