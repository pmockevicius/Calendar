package com.example.tasksapplication.di

import com.example.calendar.data.dataSource.date.local.DateLocalDataSourceInterface
import com.example.calendar.data.dataSource.date.local.DateLocalLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DateLocalDataSourceModel {

    @Provides
    fun provideDateLocalDataSource(): DateLocalDataSourceInterface =
        DateLocalLocalDataSource()
}