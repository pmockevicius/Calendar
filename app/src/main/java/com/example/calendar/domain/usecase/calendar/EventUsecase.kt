package com.example.calendar.domain.usecase.calendar

import com.example.calendar.data.repository.mapper.toEntity
import com.example.calendar.domain.entity.Event
import com.example.calendar.domain.repository.event.EventRepositoryInterface
import javax.inject.Inject

class EventUsecase @Inject constructor (
    private val eventRepository: EventRepositoryInterface
): EventUsecaseInterface {

    override suspend fun addEvent(event: Event){
        eventRepository.addEvent(event)
    }

    override suspend fun getEvents(): List<Event>{
        return eventRepository.getEvents()
    }

}