package com.example.calendar.presentation.features.calendar

import com.example.calendar.domain.entity.Day

interface CalendarCallbackInterface {

    fun daySelected(day: Day)
}