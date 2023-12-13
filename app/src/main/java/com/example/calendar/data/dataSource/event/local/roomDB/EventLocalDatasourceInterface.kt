package com.example.calendar.data.dataSource.event.local.roomDB

import com.example.calendar.domain.entity.Event

interface EventLocalDatasourceInterface {

    fun addEvent(eventDbo: EventDbo)
    fun getEventsFor(year: Int, month: Int): List<EventDbo>
    suspend fun getEventsFor(year: Int, month: Int, day: Int): List<EventDbo>
}
