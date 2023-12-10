package com.example.calendar.presentation.features.calendar

import kotlinx.coroutines.flow.StateFlow

interface CalendarViewModelInterface {

    fun loadCurrentMonth()
    val uiState: StateFlow<uiState>
    fun displayPreviousMonth()
    fun displayNextMonth()
}
