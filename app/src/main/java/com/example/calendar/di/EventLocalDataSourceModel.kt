package com.example.tasksapplication.di

import com.example.calendar.data.dataSource.date.local.DateLocalDataSourceInterface
import com.example.calendar.data.dataSource.date.local.DateLocalLocalDataSource
import com.example.calendar.data.dataSource.event.local.roomDB.EventLocalDataSource
import com.example.calendar.data.dataSource.event.local.roomDB.EventLocalDatasourceInterface
import com.sidharth.mosam.data.local.CalendarDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class EventLocalDataSourceModel {

    @Provides
    fun provideEventLocalDataSource(dataBase: CalendarDataBase): EventLocalDatasourceInterface =
       EventLocalDataSource(dataBase)
}