package com.example.calendar.domain.entity

data class SelectedMonth(
    val monthName: String = "",
    val monthInNumber: Int = 0,
    val year: Int = 0,
    val firstWeekDayOfMonth: String = "",
    val lengthOfMonth: Int = 0
)
