package com.example.calendar.data.repository.date

import com.example.calendar.data.dataSource.date.local.DateLocalDataSourceInterface
import com.example.calendar.data.repository.mapper.toEntity
import com.example.calendar.domain.entity.SelectedMonth
import com.example.calendar.domain.repository.date.DateRepositoryInterface
import javax.inject.Inject

class DateRepository @Inject constructor (
    private val localDateDS: DateLocalDataSourceInterface
) : DateRepositoryInterface {

    override fun getDateBy(year: Int, month: Int): SelectedMonth {
        return localDateDS.getDateBy(year, month).toEntity()
    }

}