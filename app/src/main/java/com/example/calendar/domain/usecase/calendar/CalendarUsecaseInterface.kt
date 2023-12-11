package com.example.calendar.domain.usecase.calendar


import java.time.LocalDate

interface CalendarUsecaseInterface {

    fun getDateBy(year: Int, month: Int): LocalDate
}
