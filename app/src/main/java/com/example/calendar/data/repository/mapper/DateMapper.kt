package com.example.calendar.data.repository.mapper

import com.example.calendar.domain.entity.Day
import com.example.calendar.domain.entity.SelectedMonth
import java.time.LocalDate

fun LocalDate.toEntity(): SelectedMonth =
    SelectedMonth(
        monthName = this.month.toString(),
        monthInNumber = this.monthValue,
        year = this.year,
        firstWeekDayOfMonth = this.dayOfWeek.toString(),
        lengthOfMonth = this.lengthOfMonth(),
        daysOfTheMonth = (1..this.lengthOfMonth()).map { day ->
            Day(
                day = day,
                month = this.monthValue,
                year = this.year,
                hasEvents = false,
                isCurrentDay = this.isEqual(LocalDate.now())
            )
        }
    )


