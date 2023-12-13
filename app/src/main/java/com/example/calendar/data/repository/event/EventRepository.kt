package com.example.calendar.data.repository.event

import com.example.calendar.data.dataSource.event.local.roomDB.EventLocalDatasourceInterface
import com.example.calendar.data.repository.mapper.toDBO
import com.example.calendar.data.repository.mapper.toEntity
import com.example.calendar.domain.entity.Event
import com.example.calendar.domain.repository.event.EventRepositoryInterface
import javax.inject.Inject

class EventRepository @Inject constructor(private val localEventDS: EventLocalDatasourceInterface): EventRepositoryInterface {
    override fun addEvent(event: Event) {
        localEventDS.addEvent(event.toDBO())
    }

    override fun getEventsFor(year: Int, month: Int): List<Event>{
        return localEventDS.getEventsFor(year, month).map { it.toEntity() }
    }
}