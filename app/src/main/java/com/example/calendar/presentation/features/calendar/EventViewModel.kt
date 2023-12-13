package com.example.calendar.presentation.features.calendar

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
import java.time.LocalDate
import javax.inject.Inject

data class UIState(
    val daysWithEvents: List<Int> = listOf(),
    val somethingElse: Int = 0
)

@HiltViewModel
class EventViewModel @Inject constructor(private val usecase: EventUsecaseInterface) :
    MainActivityViewModelInterface, ViewModel() {

    private val _uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState())
    override val uiState: StateFlow<UIState> = _uiState

        override fun addEvent(event: Event){
            viewModelScope.launch(Dispatchers.IO) {
                usecase.addEvent(event)
            }
        }

    override fun getDaysWithEvents(year: Int, month: Int) {
        viewModelScope.launch(Dispatchers.IO) {
           val daysWithEvents =  usecase.getDaysWithEventsList(year, month)

            _uiState.update { it.copy(daysWithEvents = daysWithEvents) }

        }
    }

    override fun loadCurrentMonth(){
        viewModelScope.launch(Dispatchers.IO) {
            val currentDate = LocalDate.now()
            val daysWithEvents = usecase.getDaysWithEventsList(currentDate.year, currentDate.monthValue)

            _uiState.update { it.copy(daysWithEvents = daysWithEvents) }
        }

    }



}