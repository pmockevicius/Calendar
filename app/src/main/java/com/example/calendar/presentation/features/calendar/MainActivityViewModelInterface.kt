package com.example.calendar.presentation.features.calendar

import com.example.calendar.domain.entity.Event
import kotlinx.coroutines.flow.StateFlow

interface MainActivityViewModelInterface {

    fun addEvent(event: Event)

    fun getDaysWithEvents(year: Int, month:Int)
    val uiState: StateFlow<UIState>
    fun loadCurrentMonth()
}
