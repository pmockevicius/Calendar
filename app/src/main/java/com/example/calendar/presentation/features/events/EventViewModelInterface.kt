package com.example.calendar.presentation.features.events

import com.example.calendar.domain.entity.Event
import kotlinx.coroutines.flow.StateFlow

interface EventViewModelInterface {

    fun addEvent(event: Event)

    fun getDaysWithEvents(year: Int, month:Int)
    val uiState: StateFlow<UIState>
    fun loadCurrentMonth()
    fun getEventsFor(year: Int, month: Int, day: Int)
}
