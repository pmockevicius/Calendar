package com.example.calendar.presentation.features.calendar.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.R
import com.example.calendar.domain.entity.Day


class CalendarAdapter(
    private var days: List<Day>
) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    inner class CalendarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dayTextView: TextView = view.findViewById(R.id.dayCellText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.calendar_cell, parent, false)
        return CalendarViewHolder(view)
    }

    override fun getItemCount(): Int {
        return days.size
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val day = days[position]
        val dayString = if (day.day == 0) "" else day.day.toString()

        holder.dayTextView.text = dayString
    }


    fun updateDaysOfTheMont(listOfDays: List<Day>) {
        days = listOfDays
        notifyDataSetChanged()
    }
}
