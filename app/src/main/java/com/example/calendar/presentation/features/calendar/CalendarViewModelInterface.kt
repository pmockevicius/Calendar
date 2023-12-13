package com.example.calendar.presentation.features.calendar

import kotlinx.coroutines.flow.StateFlow

interface CalendarViewModelInterface {

    val uiState: StateFlow<uiState>
    fun loadCurrentMonth()

    fun displayPreviousMonth()
    fun displayNextMonth()
}
