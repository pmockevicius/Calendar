package com.example.calendar.data.dataSource.date.local

import java.time.LocalDate

interface DateLocalDataSourceInterface {

    fun getDateBy(year: Int, month: Int): LocalDate
}
