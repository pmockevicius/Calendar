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
import com.example.calendar.databinding.CustomCalendarViewBinding
import java.time.DayOfWeek
import java.time.LocalDate

data class CustomCalendarDay(
    val day: Int = 0,
    val month: Int = 0,
    val year: Int = 0,
    val hasEvents: Boolean = false,
)

class CustomCalendarLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val binding: CustomCalendarViewBinding =
        CustomCalendarViewBinding.inflate(LayoutInflater.from(context), this, true)

    private val recyclerView: RecyclerView
    private lateinit var adapter: CustomCalendarAdapter
    private var selectedMonth: Int = 0
    private var selectedYear: Int = 0
    private var eventDays: List<Int> = listOf()

    init {
        inflate(context, R.layout.custom_calendar_view, this)

        recyclerView = binding.calendarRV
        val layoutManager = GridLayoutManager(context, 7)
        recyclerView.layoutManager = layoutManager

        adapter = CustomCalendarAdapter(listOf())
        recyclerView.adapter = adapter

        loadCurrentDate()
        initListeners()
    }


    fun setEvents(daysWithEvents: List<Int>) {
        eventDays = daysWithEvents
        adapter?.let {
            updateEventsInAdapter()
        }
    }

    private fun updateEventsInAdapter() {
        val daysOfMonth = generateDaysOfMonthList(LocalDate.of(selectedYear, selectedMonth, 1))
        adapter.updateDaysOfTheMont(daysOfMonth)
    }

    private fun initListeners() {

        binding.monthNavigationPrevious.setOnClickListener() {
            displayPreviousMonth()
            calendarClickListener?.onPreviousMonthClick(selectedYear, selectedMonth)

        }

        binding.monthNavigationNext.setOnClickListener() {
            displayNextMonth()
            calendarClickListener?.onNextMonthClick(selectedYear, selectedMonth)
        }

        binding.monthTextView.setOnClickListener() {
            loadCurrentDate()
            calendarClickListener?.onCurrentMonthClick(selectedYear, selectedMonth)

        }
    }

    private fun loadCurrentDate() {
        val currentDate = LocalDate.now()
        selectedMonth = currentDate.monthValue
        selectedYear = currentDate.year
        val currentMonth = LocalDate.of(selectedYear, selectedMonth, 1)
        val daysOfMonth = generateDaysOfMonthList(
            currentMonth,
        )
        adapter.updateDaysOfTheMont(daysOfMonth)
        setMonthYearText(currentMonth.month.toString(), currentMonth.year)

    }

    private fun displayPreviousMonth() {
        if (selectedMonth > 1) {
            selectedMonth--
        } else {
            selectedMonth = 12
            selectedYear--
        }
        val previousMonth = getMonthBy(selectedYear, selectedMonth)
        val daysOfMonth =
            generateDaysOfMonthList(previousMonth)

        adapter.updateDaysOfTheMont(daysOfMonth)
        setMonthYearText(previousMonth.month.toString(), previousMonth.year)

    }

    private fun displayNextMonth() {
        if (selectedMonth < 12) {
            selectedMonth++
        } else {
            selectedMonth = 1
            selectedYear++
        }

        val nextMonth = getMonthBy(selectedYear, selectedMonth)
        val daysOfMonth =
            generateDaysOfMonthList(nextMonth)

        adapter.updateDaysOfTheMont(daysOfMonth)
        setMonthYearText(nextMonth.month.toString(), nextMonth.year)

    }

    private fun getMonthBy(year: Int, month: Int): LocalDate {
        return LocalDate.of(year, month, 1)
    }

    private fun setMonthYearText(month: String, year: Int) {
        binding.monthTextView.text = "$month $year"
    }

    private fun generateDaysOfMonthList(
        selectedMonth: LocalDate,
    ): List<CustomCalendarDay> {
        val emptySpacesForDaysList = when (selectedMonth.dayOfWeek) {
            DayOfWeek.MONDAY -> 1
            DayOfWeek.TUESDAY -> 2
            DayOfWeek.WEDNESDAY -> 3
            DayOfWeek.THURSDAY -> 4
            DayOfWeek.FRIDAY -> 5
            DayOfWeek.SATURDAY -> 6
            else -> 0
        }

        val emptySpaces = List(emptySpacesForDaysList) { CustomCalendarDay() }
        val daysOfMonth = (1..selectedMonth.lengthOfMonth()).map { day ->
            CustomCalendarDay(
                day = day,
                month = selectedMonth.monthValue,
                year = selectedMonth.year,
                hasEvents = false,
            )
        }

        val daysWithEvents = daysOfMonth.map { day ->
            day.copy(hasEvents = eventDays.contains(day.day))
        }
        return emptySpaces + daysWithEvents
    }

    inner class CustomCalendarAdapter(
        private var days: List<CustomCalendarDay>,
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

            if (day.hasEvents) {
                holder.eventNotifier.visibility = View.VISIBLE
            }

            setBackgroundForToday(day, holder)

            holder.itemView.setOnClickListener {
                calendarClickListener?.onDayClick(day.year, day.month, day.day)
            }

        }

        fun updateDaysOfTheMont(listOfDays: List<CustomCalendarDay>) {
            days = listOfDays
            notifyDataSetChanged()
        }

        private fun setBackgroundForToday(day: CustomCalendarDay, holder: CalendarViewHolder) {

            if (day.day > 0 && day.month > 0 && day.year > 0) {
                val currentDate = LocalDate.now()
                val todayDate = LocalDate.of(day.year, day.month, day.day)
                val dayBackground = if (todayDate == currentDate) Color.RED else Color.TRANSPARENT
                holder.dayTextView.setBackgroundColor(dayBackground)
            }
        }

        inner class CalendarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val dayTextView: TextView = view.findViewById(R.id.dayCellText)
            val eventNotifier: TextView = view.findViewById(R.id.eventNotifier)


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


    interface CalendarClickListener {
        fun onDayClick(selectedYear: Int, selectedMonth: Int, selectedDay: Int)
        fun onPreviousMonthClick(previousYear: Int, previousMonth: Int)
        fun onNextMonthClick(nextYear: Int, nextMonth: Int)
        fun onCurrentMonthClick(currentYear: Int, currentMonth: Int)

    }

    private var calendarClickListener: CalendarClickListener? = null

    fun setOnCalendarClickListener(listener: CalendarClickListener) {
        this.calendarClickListener = listener
    }

}
