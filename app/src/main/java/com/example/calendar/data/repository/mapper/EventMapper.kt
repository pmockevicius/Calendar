package com.example.calendar.data.repository.mapper

import com.example.calendar.data.dataSource.event.local.roomDB.EventDbo
import com.example.calendar.domain.entity.Event


fun Event.toDBO(): EventDbo =
    EventDbo(
        id = 0,
        name = this.name,
        eventDay = this.eventDay,
        eventMonth = this.eventMonth,
        eventYear = this.eventYear,
        eventTime = this.eventTime,
        dateAdded = this.dateAdded,
        location = this.location
    )

fun EventDbo.toEntity(): Event =
    Event(
        name = this.name,
        eventDay = this.eventDay,
        eventMonth = this.eventMonth,
        eventYear = this.eventYear,
        eventTime = this.eventTime,
        dateAdded = this.dateAdded,
        location = this.location
    )


