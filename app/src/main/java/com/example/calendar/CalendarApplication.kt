package com.example.calendar

import android.app.Application
import com.sidharth.mosam.data.local.EventDataBase
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class CalendarApplication : Application() {

    val database: EventDataBase by lazy { EventDataBase.getDatabase(applicationContext) }


}