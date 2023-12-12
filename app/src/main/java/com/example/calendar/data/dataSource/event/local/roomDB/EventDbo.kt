package com.example.calendar.data.dataSource.event.local.roomDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "events")
data class EventDbo(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String = "",
    val eventDate: String = "",
    val dateAdded: Long,
    val location: String = ""
) {
}