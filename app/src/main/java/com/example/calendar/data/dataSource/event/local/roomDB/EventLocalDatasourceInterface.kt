package com.example.calendar.data.dataSource.event.local.roomDB


interface EventLocalDatasourceInterface {

    fun addEvent(eventDbo: EventDbo)
    suspend fun getEvents(): List<EventDbo>
}
