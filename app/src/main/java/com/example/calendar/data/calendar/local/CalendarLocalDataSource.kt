package com.example.calendar.data.calendar.local

import android.content.ContentValues
import android.util.Log

class CalendarLocalDataSource: CalendarDatasourceInterface {

    override fun test(){
        Log.d(ContentValues.TAG, "test in datasource called: ")
    }
}