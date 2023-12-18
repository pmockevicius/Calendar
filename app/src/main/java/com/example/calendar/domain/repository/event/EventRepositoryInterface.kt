package com.example.calendar.domain.repository.event

import com.example.calendar.domain.entity.Event

interface EventRepositoryInterface {

    suspend fun addEvent(event: Event)
    suspend fun getEvents(): List<Event>
}
