package com.example.calendar

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.sidharth.mosam.data.local.CalendarDataBase
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class CalendarApplication : Application() {

    val database: CalendarDataBase by lazy { CalendarDataBase.getDatabase(applicationContext) }


}