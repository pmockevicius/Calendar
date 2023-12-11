package com.example.calendar.data.repository.event

import com.example.calendar.data.dataSource.event.local.roomDB.EventLocalDatasourceInterface
import com.example.calendar.domain.repository.event.EventRepositoryInterface
import javax.inject.Inject

class EventRepository @Inject constructor(private val localEventDS: EventLocalDatasourceInterface): EventRepositoryInterface {
    override fun insert() {
        localEventDS.insert()
    }
}