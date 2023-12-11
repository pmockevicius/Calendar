package com.example.calendar.domain.repository

import java.time.LocalDate

interface CalendarRepositoryInterface {

    fun getDateBy(year: Int, month: Int): LocalDate
}
