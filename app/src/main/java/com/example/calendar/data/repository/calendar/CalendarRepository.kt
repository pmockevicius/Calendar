package com.example.calendar.data.repository.calendar

import android.content.ContentValues
import android.util.Log
import com.example.calendar.data.calendar.local.CalendarDatasourceInterface
import com.example.calendar.domain.repository.CalendarRepositoryInterface
import java.time.LocalDate
import javax.inject.Inject

class CalendarRepository @Inject constructor (
    private val localDS: CalendarDatasourceInterface) : CalendarRepositoryInterface {

    override fun getDateBy(year: Int, month: Int): LocalDate {
        return localDS.getDateBy(year, month)
    }

}