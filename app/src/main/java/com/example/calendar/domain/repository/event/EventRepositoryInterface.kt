package com.example.calendar.domain.repository.event

import com.example.calendar.domain.entity.Event

interface EventRepositoryInterface {

    fun addEvent(event: Event)
    fun getEventsFor(year: Int, month: Int): List<Event>
    suspend fun getEventsFor(year: Int, month: Int, day: Int): List<Event>
}
