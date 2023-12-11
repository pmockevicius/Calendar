package com.example.calendar.data.calendar.local

import java.time.LocalDate

class CalendarLocalDataSource: CalendarDatasourceInterface {

    override fun getDateBy(year: Int, month: Int): LocalDate {
        return LocalDate.of(year, month, 1)
    }
}