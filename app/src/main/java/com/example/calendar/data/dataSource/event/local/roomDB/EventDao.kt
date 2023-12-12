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

    @Delete
    fun deleteTask(event: EventDbo)

    //
//    @Update
//    fun updateTask(task: TaskDbo)
//

//
//    @Query("DELETE FROM tasks WHERE body = ''")
//    fun removeTasksWithNoText()

}