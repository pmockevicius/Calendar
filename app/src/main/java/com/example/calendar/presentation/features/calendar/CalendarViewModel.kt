package com.example.calendar.presentation.features.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calendar.domain.entity.Day
import com.example.calendar.domain.usecase.calendar.CalendarUsecaseInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class uiState(
    val days: List<Day> = emptyList(),
    val monthYearText: String = ""
)

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val usecase: CalendarUsecaseInterface
) :
    CalendarViewModelInterface, ViewModel() {

    private val _uiState: MutableStateFlow<uiState> = MutableStateFlow(uiState())
    override val uiState: StateFlow<uiState> = _uiState.asStateFlow()

    private var selectedMonth = 0
    private var selectedYear = 0


    override fun loadCurrentMonth() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentDate = LocalDate.now()
            selectedMonth = currentDate.monthValue
            selectedYear = currentDate.year
            val currentMonth = usecase.getDateBy(selectedYear, selectedMonth)
            val daysOfMonth = generateDaysOfMonthList(
                currentMonth.daysOfTheMonth,
                currentMonth.firstWeekDayOfMonth
            )
            _uiState.update {
                it.copy(
                    days = daysOfMonth,
                    monthYearText = "${currentMonth.monthName} ${currentMonth.year}"
                )
            }
        }
    }

    override fun displayPreviousMonth() {
        if (selectedMonth > 1) {
            selectedMonth--
        } else {
            selectedMonth = 12
            selectedYear--
        }

        val previousMonth = usecase.getDateBy(selectedYear, selectedMonth)
        val daysOfMonth =
            generateDaysOfMonthList(previousMonth.daysOfTheMonth, previousMonth.firstWeekDayOfMonth)

        _uiState.update {
            it.copy(
                days = daysOfMonth,
                monthYearText = "${previousMonth.monthName} ${previousMonth.year}"
            )
        }
    }


    override fun displayNextMonth() {
        if (selectedMonth < 12) {
            selectedMonth++
        } else {
            selectedMonth = 1
            selectedYear++
        }

        val nextMonth = usecase.getDateBy(selectedYear, selectedMonth)
        val daysOfMonth =
            generateDaysOfMonthList(nextMonth.daysOfTheMonth, nextMonth.firstWeekDayOfMonth)

        _uiState.update {
            it.copy(
                days = daysOfMonth,
                monthYearText = "${nextMonth.monthName} ${nextMonth.year}"
            )
        }
    }

    private fun generateDaysOfMonthList(days: List<Day>, firstWeekDayOfMonth: String): List<Day> {
        val emptySpacesForDaysList = when (firstWeekDayOfMonth.lowercase()) {
            "monday" -> 1
            "tuesday" -> 2
            "wednesday" -> 3
            "thursday" -> 4
            "friday" -> 5
            "saturday" -> 6
            else -> 0
        }

        val emptySpaces = List(emptySpacesForDaysList) { Day() }
        return emptySpaces + days
    }

}