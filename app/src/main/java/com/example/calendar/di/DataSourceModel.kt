package com.example.tasksapplication.di

import com.example.calendar.data.calendar.local.CalendarDatasourceInterface
import com.example.calendar.data.calendar.local.CalendarLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModel {

    @Provides
    fun provideCalendarLocalDataSource(): CalendarDatasourceInterface =
        CalendarLocalDataSource()
}