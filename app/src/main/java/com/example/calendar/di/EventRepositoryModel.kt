package com.example.tasksapplication.di

import com.example.calendar.data.dataSource.event.local.roomDB.EventLocalDatasourceInterface
import com.example.calendar.data.repository.event.EventRepository
import com.example.calendar.domain.repository.event.EventRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class EventRepositoryModel {

    @Provides
    fun provideEventRepository(localEventDS: EventLocalDatasourceInterface): EventRepositoryInterface =
        EventRepository(
            localEventDS
        )
}
