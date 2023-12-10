package com.example.calendar.domain.usecase.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.calendar.domain.repository.CalendarRepositoryInterface
import java.time.LocalDate
import javax.inject.Inject

class CalendarUsecase @Inject constructor (
    private val repository: CalendarRepositoryInterface): CalendarUsecaseInterface {

        override fun loadCurrentMonth() :  LocalDate {
            val currentDate = LocalDate.now()
            return currentDate
        }


    override fun getDateInformation(year: Int, month: Int): LocalDate {
        val date = LocalDate.of(year, month, 1)

        // Retrieve information about the date
        val dayOfWeek = date.dayOfWeek
        val lengthOfMonth = date.lengthOfMonth()

        // Print or use the information as needed
        println("Year: $year")
        println("Month: $month")
        println("First day of the month: $dayOfWeek")
        println("Number of days in the month: $lengthOfMonth")

        return date
    }

}