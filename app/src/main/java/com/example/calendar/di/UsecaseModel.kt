package com.example.tasksapplication.di

import com.example.calendar.domain.repository.event.EventRepositoryInterface
import com.example.calendar.domain.usecase.calendar.EventUsecase
import com.example.calendar.domain.usecase.calendar.EventUsecaseInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UsecaseModel {

    @Provides
    fun provideCalendarUsecase(
        eventRepository: EventRepositoryInterface):
            EventUsecaseInterface = EventUsecase( eventRepository)
}