package com.example.calendar.data.dataSource.event.local.roomDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTaskAndGetId(event: EventDbo)

//    @Query("SELECT * FROM tasks")
//    fun getTasks(): List<TaskDbo>
//
//    @Update
//    fun updateTask(task: TaskDbo)
//
//    @Delete
//    fun deleteTask(task: TaskDbo)
//
//    @Query("DELETE FROM tasks WHERE body = ''")
//    fun removeTasksWithNoText()

}