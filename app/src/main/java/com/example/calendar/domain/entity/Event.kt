package com.example.calendar.domain.entity

data class Event(
    val name: String = "",
    val eventDate: String = "",
    val dateAdded: Long,
    val location: String = ""
)
