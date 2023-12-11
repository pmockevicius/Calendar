package com.example.calendar.presentation.features.calendar

import androidx.lifecycle.ViewModel
import com.example.calendar.domain.usecase.calendar.CalendarUsecaseInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import javax.inject.Inject

data class uiState(
    val days:  List<String> = emptyList(),
    val monthYearText: String = ""
)

@HiltViewModel
class CalendarViewModel @Inject constructor(private val usecase: CalendarUsecaseInterface) :
    CalendarViewModelInterface, ViewModel() {

    private val _uiState: MutableStateFlow<uiState> = MutableStateFlow(uiState())
    override val uiState: StateFlow<uiState> = _uiState.asStateFlow()

    private var selectedMonth = 0
    private var selectedYear = 0


    override fun loadCurrentMonth() {
        val currentDate = LocalDate.now()
        selectedMonth = currentDate.monthValue
        selectedYear = currentDate.year
        val selectedDate = usecase.getDateBy(selectedYear, selectedMonth)
        val firstWeekDayOfMonth = selectedDate.dayOfWeek.toString()
        val daysOfMonth = generateDaysOfMonthList(selectedDate.lengthOfMonth(),firstWeekDayOfMonth)
        updateUIState(daysOfMonth, "${selectedDate.month} ${selectedDate.year}")
    }

    override fun displayPreviousMonth() {
        if (selectedMonth > 1) {
            selectedMonth--
        } else {
            selectedMonth = 12
            selectedYear--
        }

        val previousMonth = usecase.getDateBy(selectedYear, selectedMonth)
        val firstWeekDayOfMonth = previousMonth.dayOfWeek.toString()
        val daysOfMonth = generateDaysOfMonthList(previousMonth.lengthOfMonth(),firstWeekDayOfMonth)

        updateUIState(daysOfMonth, "${previousMonth.month} ${previousMonth.year}")
    }


    override fun displayNextMonth() {
        if (selectedMonth < 12) {
            selectedMonth++
        } else {
            selectedMonth = 1
            selectedYear++
        }

        val nextMonth = usecase.getDateBy(selectedYear, selectedMonth)
        val firstWeekDayOfMonth = nextMonth.dayOfWeek.toString()
        val daysOfMonth = generateDaysOfMonthList(nextMonth.lengthOfMonth(),firstWeekDayOfMonth)
        updateUIState(daysOfMonth, "${nextMonth.month} ${nextMonth.year}")
    }




    private fun updateUIState(
        days: List<String> = _uiState.value.days,
        monthYearText: String = _uiState.value.monthYearText,
    ) {
        _uiState.value = uiState(
            days = days,
            monthYearText = monthYearText,
        )
    }

    private fun generateDaysOfMonthList(lengthOfMonth: Int, firstWeekDayOfMonth: String): List<String> {
        val daysOfMonth = (1..lengthOfMonth).map { it.toString() }
        val emptySpacesForDaysList = when (firstWeekDayOfMonth.lowercase()) {
            "monday" -> 1
            "tuesday" -> 2
            "wednesday" -> 3
            "thursday" -> 4
            "friday" -> 5
            "saturday" -> 6
            else -> 0
        }

        val emptySpaces = List(emptySpacesForDaysList) { "" }
        return emptySpaces + daysOfMonth
    }

}