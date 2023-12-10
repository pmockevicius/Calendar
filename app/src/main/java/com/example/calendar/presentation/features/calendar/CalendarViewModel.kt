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
    val days: Int = 0,
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
        val currentMonth = usecase.getDateInformation(selectedYear, selectedMonth)

        updateUIState(currentMonth.lengthOfMonth(), "${currentMonth.month} ${currentMonth.year}")
    }

    override fun displayPreviousMonth() {
        if (selectedMonth > 1) {
            selectedMonth--
        } else {
            selectedMonth = 12
            selectedYear--
        }

        val previousMonth = usecase.getDateInformation(selectedYear, selectedMonth)
        updateUIState(previousMonth.lengthOfMonth(), "${previousMonth.month} ${previousMonth.year}")
    }


    override fun displayNextMonth() {
        if (selectedMonth < 12) {
            selectedMonth++
        } else {
            selectedMonth = 1
            selectedYear++
        }

        val nextMonth = usecase.getDateInformation(selectedYear, selectedMonth)
        updateUIState(nextMonth.lengthOfMonth(), "${nextMonth.month} ${nextMonth.year}")
    }




    private fun updateUIState(
        days: Int = _uiState.value.days,
        monthYearText: String = _uiState.value.monthYearText,

    ) {
        _uiState.value = uiState(
            days = days,
            monthYearText = monthYearText,

        )
    }


}