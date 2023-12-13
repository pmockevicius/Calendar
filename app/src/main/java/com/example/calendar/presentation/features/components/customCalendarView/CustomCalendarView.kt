// CustomButtonLayout.kt

package com.example.circularimageview.components

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.R
import java.time.LocalDate


data class CustomDay(
    val day: Int = 0,
    val month: Int = 0,
    val year: Int = 0,
    val hasEvents: Boolean = false,
)


class CustomCalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val recyclerView: RecyclerView
    private val adapter: CustomCalendarAdapter
    private var selectedMonth: Int = 0
    private var selectedYear: Int = 0

    init {
        inflate(context, R.layout.custom_calendar_view, this)

        recyclerView = findViewById(R.id.calendarRV)
        val layoutManager = GridLayoutManager(context, 7)
        recyclerView.layoutManager = layoutManager

        adapter = CustomCalendarAdapter(listOf())
        recyclerView.adapter = adapter

        loadCurrentDate()
    }


    private fun loadCurrentDate() {
        val currentDate = LocalDate.now()
        selectedMonth = currentDate.monthValue
        selectedYear = currentDate.year
        val currentMonth = LocalDate.of(selectedYear, selectedMonth, 1)
        val daysOfMonth = generateDaysOfMonthList(
            currentMonth,
            currentMonth.dayOfWeek.toString()
        )

        adapter.updateDaysOfTheMont(daysOfMonth)


    }

    private fun generateDaysOfMonthList(
        selectedMonth: LocalDate,
        firstWeekDayOfMonth: String
    ): List<CustomDay> {
        val emptySpacesForDaysList = when (firstWeekDayOfMonth.lowercase()) {
            "monday" -> 1
            "tuesday" -> 2
            "wednesday" -> 3
            "thursday" -> 4
            "friday" -> 5
            "saturday" -> 6
            else -> 0
        }

        val emptySpaces = List(emptySpacesForDaysList) { CustomDay() }
        val daysOfMonth = (1..selectedMonth.lengthOfMonth()).map { day ->
            CustomDay(
                day = day,
                month = selectedMonth.monthValue,
                year = selectedMonth.year,
                hasEvents = false,
            )
        }
        return emptySpaces + daysOfMonth
    }

    inner class CustomCalendarAdapter(
        private var days: List<CustomDay>,
    ) : RecyclerView.Adapter<CustomCalendarAdapter.CalendarViewHolder>() {


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

            setBackgroundForToday(day, holder)
        }

        fun updateDaysOfTheMont(listOfDays: List<CustomDay>) {
            days = listOfDays
            notifyDataSetChanged()
        }

            private fun setBackgroundForToday(day: CustomDay, holder: CalendarViewHolder){

        if (day.day > 0 && day.month > 0 && day.year > 0) {
            val currentDate = LocalDate.now()
            val todayDate = LocalDate.of(day.year, day.month, day.day)
            val dayBackground = if(todayDate == currentDate) Color.RED else Color.TRANSPARENT
            holder.dayTextView.setBackgroundColor(dayBackground)
        }
    }

        inner class CalendarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val dayTextView: TextView = view.findViewById(R.id.dayCellText)

            init {
                itemView.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val clickedDay = days[position]

                    }
                }
            }
        }

    }




}
