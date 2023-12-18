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
    fun getEvents(): List<EventDbo>

    @Delete
    fun DeleteEvent(event: EventDbo)


}