package com.example.tasksapplication.di

import com.example.calendar.data.calendar.local.CalendarDatasourceInterface
import com.example.calendar.data.repository.calendar.CalendarRepository
import com.example.calendar.domain.repository.CalendarRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModel {

    @Provides
    fun provideCalendarRepository(localDS: CalendarDatasourceInterface): CalendarRepositoryInterface =
        CalendarRepository(
            localDS
        )
}
