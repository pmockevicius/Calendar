package com.example.calendar.data.dataSource.event.local.roomDB

interface EventLocalDatasourceInterface {

    fun addEvent(eventDbo: EventDbo)
    fun getEventsFor(year: Int, month: Int): List<EventDbo>
}
