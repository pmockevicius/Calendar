package com.example.calendar.presentation.features.events

import com.example.calendar.domain.entity.Event
import kotlinx.coroutines.flow.StateFlow

interface EventViewModelInterface {

    val uiState: StateFlow<UIState>
    fun addEvent(event: Event)
    fun getEvents()
}
