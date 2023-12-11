package com.example.calendar.domain.repository

import com.example.calendar.domain.entity.SelectedMonth

interface DateRepositoryInterface {

    fun getDateBy(year: Int, month: Int): SelectedMonth
}
