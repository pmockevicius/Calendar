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

    override fun getEventsFor(year: Int, month: Int): List<EventDbo>{

       val result = eventDao.getEventsFor(year, month)

        Log.d(TAG, "getEvents in Datasource: $result")

        return result
    }

    override suspend fun getEventsFor(year: Int, month: Int, day: Int): List<EventDbo>{
        return eventDao.getEventsFor(year, month, day)
    }

}