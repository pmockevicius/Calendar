package com.example.calendar.domain.usecase.calendar

import com.example.calendar.domain.repository.CalendarRepositoryInterface
import java.time.LocalDate
import javax.inject.Inject

class CalendarUsecase @Inject constructor (
    private val repository: CalendarRepositoryInterface): CalendarUsecaseInterface {

    override fun getDateBy(year: Int, month: Int): LocalDate {
        return repository.getDateBy(year, month)
    }
}