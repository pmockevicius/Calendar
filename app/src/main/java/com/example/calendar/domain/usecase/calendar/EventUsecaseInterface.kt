package com.example.calendar.domain.usecase.calendar

import com.example.calendar.domain.entity.Event


interface EventUsecaseInterface {

    suspend fun addEvent(event: Event)
    suspend fun getEvents(): List<Event>
}
