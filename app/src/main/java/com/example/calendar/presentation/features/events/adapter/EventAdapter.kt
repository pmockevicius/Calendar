package com.example.rickmorty.presentation.features.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.R
import com.example.calendar.domain.entity.Event
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EventAdapter(
    private var events: List<Event>,
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_list_item, parent, false)
        return EventViewHolder(view)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.eventName.setText(event.name)
        holder.eventLocation.setText(event.location)
        holder.eventTime.setText(event.eventTime)

    }


    inner class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val eventName: EditText = view.findViewById(R.id.EventName)
        val eventLocation: EditText = view.findViewById(R.id.EventLocation)
        val eventTime: EditText = view.findViewById(R.id.EventDateAdded)

    }

    fun updateEvents(newEvents: List<Event>) {
        events = newEvents
   notifyDataSetChanged()
    }
}
