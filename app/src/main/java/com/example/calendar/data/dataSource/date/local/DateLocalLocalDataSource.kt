package com.example.calendar.data.dataSource.date.local

import java.time.LocalDate

class DateLocalLocalDataSource: DateLocalDataSourceInterface {

    override fun getDateBy(year: Int, month: Int): LocalDate {
        return LocalDate.of(year, month, 1)
    }
}