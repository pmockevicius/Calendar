package com.example.tasksapplication.di

import com.example.calendar.domain.repository.CalendarRepositoryInterface
import com.example.calendar.domain.usecase.calendar.CalendarUsecase
import com.example.calendar.domain.usecase.calendar.CalendarUsecaseInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UsecaseModel {

    @Provides
    fun provideCalendarUsecase(repository: CalendarRepositoryInterface): CalendarUsecaseInterface =
        CalendarUsecase(repository)
}