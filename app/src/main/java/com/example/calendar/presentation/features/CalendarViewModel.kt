package com.example.calendar.presentation.features

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.calendar.domain.repository.CalendarRepositoryInterface
import com.example.calendar.domain.usecase.calendar.CalendarUsecaseInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class CalendarViewModel @Inject constructor(private val usecase: CalendarUsecaseInterface) :
    CalendarViewModelInterface, ViewModel(){

    override fun test(){
        usecase.test()
    }
}