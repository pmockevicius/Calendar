package com.example.calendar.domain.usecase.calendar


import java.time.LocalDate

interface CalendarUsecaseInterface {

    fun loadCurrentMonth() : LocalDate

    fun getDateInformation(year: Int, month: Int): LocalDate
}
