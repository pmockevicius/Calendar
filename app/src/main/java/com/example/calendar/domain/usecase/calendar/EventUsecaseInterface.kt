package com.example.calendar.domain.usecase.calendar

import com.example.calendar.domain.entity.Event


interface EventUsecaseInterface {

    fun addEvent(event: Event)
    fun getEventsFor(year: Int, month: Int): List<Event>
    fun getDaysWithEventsList(year: Int, month: Int): List<Int>
    suspend fun getEventsFor(year: Int, month: Int, day: Int): List<Event>
}
