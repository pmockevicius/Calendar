package com.example.calendar.domain.entity

data class Day(
    val day: Int = 0,
    val month: Int = 0,
    val year: Int = 0,
    val hasEvents: Boolean = false,
    val isCurrentDay: Boolean = false
)
