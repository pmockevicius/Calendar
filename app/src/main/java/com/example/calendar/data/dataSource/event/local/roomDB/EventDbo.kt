package com.example.calendar.data.dataSource.event.local.roomDB

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "events")
data class EventDbo(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String = "",
    val eventDay: Int = 0,
    val eventMonth: Int = 0,
    val eventYear: Int = 0,
    val eventTime: String = "",
    val dateAdded: Long,
    val location: String = ""
) {
}