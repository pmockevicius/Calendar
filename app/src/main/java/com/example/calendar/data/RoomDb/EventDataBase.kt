package com.sidharth.mosam.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.calendar.data.dataSource.event.local.roomDB.EventDao
import com.example.calendar.data.dataSource.event.local.roomDB.EventDbo


@Database(entities = [EventDbo::class], version = 1, exportSchema = false)
abstract class EventDataBase : RoomDatabase() {

    abstract fun eventDao(): EventDao

    companion object {
        @Volatile
        private var INSTANCE: EventDataBase? = null

        fun getDatabase(context: Context): EventDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EventDataBase::class.java,
                    "Events"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
