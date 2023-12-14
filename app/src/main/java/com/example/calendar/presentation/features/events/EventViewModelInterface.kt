package com.example.calendar.presentation.features.events

import com.example.calendar.domain.entity.Event
import kotlinx.coroutines.flow.StateFlow

interface EventViewModelInterface {

    val uiState: StateFlow<UIState>
    fun addEvent(event: Event)

    fun getDaysWithEventsFor(year: Int, month:Int)

    fun loadCurrentMonth()
    fun getEventsFor(year: Int, month: Int, day: Int)
}
