package com.example.calendar.domain.usecase.calendar

import com.example.calendar.data.repository.mapper.toEntity
import com.example.calendar.domain.entity.Event
import com.example.calendar.domain.repository.event.EventRepositoryInterface
import javax.inject.Inject

class EventUsecase @Inject constructor (
    private val eventRepository: EventRepositoryInterface
): EventUsecaseInterface {

    override fun addEvent(event: Event){
        eventRepository.addEvent(event)
    }

    override suspend fun getDaysWithEventsList(year: Int, month: Int): List<Int>{
        return eventRepository.getEventsFor(year, month, null).map { it.eventDay }
    }

    override suspend fun getEventsFor(year: Int, month: Int, day: Int?): List<Event>{
        return eventRepository.getEventsFor(year, month, day)
    }

    override suspend fun getEvents(): List<Event>{
        return eventRepository.getEvents()
    }

}