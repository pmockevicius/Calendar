package com.example.calendar.data.dataSource.event.local.roomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.calendar.domain.entity.Event

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addEvent(event: EventDbo)

    @Query("SELECT * FROM events")
    fun getEvents(): List<Event>

    @Query("SELECT * FROM events WHERE eventYear = :year AND eventMonth = :month AND (:day IS NULL OR eventDay = :day)")
    fun getEventsFor(year: Int, month: Int, day: Int? = null): List<EventDbo>



    @Delete
    fun DeleteEvent(event: EventDbo)


}