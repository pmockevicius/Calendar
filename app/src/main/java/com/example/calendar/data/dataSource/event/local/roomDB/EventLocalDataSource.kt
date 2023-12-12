package com.example.calendar.data.dataSource.event.local.roomDB

import com.sidharth.mosam.data.local.CalendarDataBase
import javax.inject.Inject

class EventLocalDataSource @Inject constructor(
    database: CalendarDataBase): EventLocalDatasourceInterface {

        private val eventDao = database.eventDao()

    override fun insert(){
        eventDao.addEvent(EventDbo(0,"Test", 23232323))
    }

}