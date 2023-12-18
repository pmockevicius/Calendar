package com.example.calendar.data.dataSource.event.local.roomDB

import android.content.ContentValues.TAG
import android.util.Log
import com.example.calendar.domain.entity.Event
import com.sidharth.mosam.data.local.EventDataBase
import javax.inject.Inject

class EventLocalDataSource @Inject constructor(
    database: EventDataBase): EventLocalDatasourceInterface {

        private val eventDao = database.eventDao()

    override fun addEvent(event: EventDbo){
     eventDao.addEvent(event)
    }

    override suspend fun getEvents(): List<EventDbo>{
        return eventDao.getEvents()
    }

}