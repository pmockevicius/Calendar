package com.example.calendar.domain.usecase.calendar


import com.example.calendar.domain.entity.SelectedMonth
import java.time.LocalDate

interface CalendarUsecaseInterface {

    fun getDateBy(year: Int, month: Int): SelectedMonth
}
