package com.example.calendar.data.calendar.local

import java.time.LocalDate

interface CalendarDatasourceInterface {

    fun getDateBy(year: Int, month: Int): LocalDate
}
