package com.example.calendar.domain.entity

data class Event(
    val name: String = "",
    val eventDay: Int = 0,
    val eventMonth: Int = 0,
    val eventYear: Int = 0,
    val dateAdded: Long,
    val location: String = ""
)
