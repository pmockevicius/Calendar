package com.example.calendar.domain.usecase.calendar

import com.example.calendar.domain.entity.SelectedMonth
import com.example.calendar.domain.repository.date.DateRepositoryInterface
import com.example.calendar.domain.repository.event.EventRepositoryInterface
import javax.inject.Inject

class CalendarUsecase @Inject constructor (
    private val dateRepository: DateRepositoryInterface,
    private val eventRepository: EventRepositoryInterface
): CalendarUsecaseInterface {

    override fun getDateBy(year: Int, month: Int): SelectedMonth {
        return dateRepository.getDateBy(year, month)
    }
}