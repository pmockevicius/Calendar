package com.example.calendar.data.repository.event

import com.example.calendar.data.dataSource.event.local.roomDB.EventDbo
import com.example.calendar.data.dataSource.event.local.roomDB.EventLocalDatasourceInterface
import com.example.calendar.data.repository.mapper.toDBO
import com.example.calendar.data.repository.mapper.toEntity
import com.example.calendar.domain.entity.Event
import com.example.calendar.domain.repository.event.EventRepositoryInterface
import javax.inject.Inject

class EventRepository @Inject constructor(private val localEventDS: EventLocalDatasourceInterface): EventRepositoryInterface {
    override suspend fun addEvent(event: Event) {
        localEventDS.addEvent(event.toDBO())
    }

    override suspend fun getEvents(): List<Event>{
        return localEventDS.getEvents().map { it.toEntity() }
    }

}