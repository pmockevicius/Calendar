package com.example.calendar.domain.usecase.calendar

import android.content.ContentValues.TAG
import android.util.Log
import com.example.calendar.domain.repository.CalendarRepositoryInterface
import javax.inject.Inject

class CalendarUsecase @Inject constructor (
    private val repository: CalendarRepositoryInterface): CalendarUsecaseInterface {

        override fun test(){
            repository.test()
        }

}