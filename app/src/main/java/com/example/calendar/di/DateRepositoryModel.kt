package com.example.tasksapplication.di

import com.example.calendar.data.dataSource.date.local.DateLocalDataSourceInterface
import com.example.calendar.data.repository.date.DateRepository
import com.example.calendar.domain.repository.date.DateRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DateRepositoryModel {

    @Provides
    fun provideCalendarRepository(localDS: DateLocalDataSourceInterface): DateRepositoryInterface =
        DateRepository(
            localDS
        )
}
