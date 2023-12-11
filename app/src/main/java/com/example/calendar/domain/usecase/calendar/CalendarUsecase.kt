package com.example.calendar.domain.usecase.calendar

import com.example.calendar.domain.entity.SelectedMonth
import com.example.calendar.domain.repository.DateRepositoryInterface
import javax.inject.Inject

class CalendarUsecase @Inject constructor (
    private val repository: DateRepositoryInterface): CalendarUsecaseInterface {

    override fun getDateBy(year: Int, month: Int): SelectedMonth {
        return repository.getDateBy(year, month)
    }
}