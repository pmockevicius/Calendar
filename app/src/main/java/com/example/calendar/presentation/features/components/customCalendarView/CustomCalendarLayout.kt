package com.example.circularimageview.components

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.R
import com.example.calendar.databinding.CustomCalendarViewBinding
import com.example.calendar.domain.entity.Event
import java.time.DayOfWeek
import java.time.LocalDate

data class CustomCalendarDay(
    val day: Int = 0,
    val month: Int = 0,
    val year: Int = 0,
    val events: List<Event> = listOf(),
)

class CustomCalendarLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val binding: CustomCalendarViewBinding =
        CustomCalendarViewBinding.inflate(LayoutInflater.from(context), this, true)


    private var adapter: CustomCalendarAdapter
    private val recyclerView: RecyclerView
    private var selectedMonth: Int = 0
    private var selectedYear: Int = 0
    private var events: List<Event> = listOf()
    private var firstDayOfWeekSun: Boolean = true
    private var selectedDay: CustomCalendarDay? = null
    private var selectedDayColor: ColorStateList
    private lateinit var todayBackgroundColor: ColorStateList

    init {
        inflate(context, R.layout.custom_calendar_view, this)


        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomCalendarLayout)
        val headerBackgroundColor = typedArray.getColor(
            R.styleable.CustomCalendarLayout_headerBackgroundColor,
            ContextCompat.getColor(context, android.R.color.transparent)
        )
        val headerTextColor = typedArray.getColor(
            R.styleable.CustomCalendarLayout_headerTextColor,
            ContextCompat.getColor(context, android.R.color.transparent)
        )

        val calendarBackgroundColor = typedArray.getColor(
            R.styleable.CustomCalendarLayout_calendarBackgroundColor,
            ContextCompat.getColor(context, android.R.color.transparent)
        )

        val selectedDayTextColor = typedArray.getColor(
            R.styleable.CustomCalendarLayout_selectedDayTextColor,
            ContextCompat.getColor(context, android.R.color.transparent)
        )

        val currentDayTextColor = typedArray.getColor(
            R.styleable.CustomCalendarLayout_currentDayBackgroundColor,
            ContextCompat.getColor(context, android.R.color.transparent)
        )

        val firstWeekDaySun = typedArray.getBoolean(
            R.styleable.CustomCalendarLayout_firstWeekDaySun,
            false
        )

        typedArray.recycle()

        binding.header.setBackgroundColor(headerBackgroundColor)
        binding.monthTextView.setTextColor(headerTextColor)
        binding.monthNavigationNext.setTextColor(headerTextColor)
        binding.monthNavigationPrevious.setTextColor(headerTextColor)
        binding.calendarWrapper.setBackgroundColor(calendarBackgroundColor)
        val iconTint = ColorStateList.valueOf(headerTextColor)
        binding.monthNavigationNext.iconTint = iconTint
        binding.monthNavigationPrevious.iconTint = iconTint
        selectedDayColor = ColorStateList.valueOf(selectedDayTextColor)
        todayBackgroundColor = ColorStateList.valueOf(currentDayTextColor)




        if (firstWeekDaySun) {
            firstDayOfWeekSun = true
            binding.weekdaysSunFirst.visibility = View.VISIBLE
            binding.weekdaysMonFirst.visibility = View.GONE
        } else {
            firstDayOfWeekSun = false
            binding.weekdaysSunFirst.visibility = View.GONE
            binding.weekdaysMonFirst.visibility = View.VISIBLE
        }


        recyclerView = binding.calendarRV
        val layoutManager = GridLayoutManager(context, 7)
        recyclerView.layoutManager = layoutManager

        adapter = CustomCalendarAdapter(listOf())
        recyclerView.adapter = adapter

        loadCurrentDate()
        initListeners()
    }

    fun setEvents(newEvents: List<Event>) {
        events = newEvents
        adapter?.let {
            updateEvents()
        }
    }

    private fun updateEvents() {
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
            DayOfWeek.MONDAY -> if (firstDayOfWeekSun) 1 else 0
            DayOfWeek.TUESDAY -> if (firstDayOfWeekSun) 2 else 1
            DayOfWeek.WEDNESDAY -> if (firstDayOfWeekSun) 3 else 2
            DayOfWeek.THURSDAY -> if (firstDayOfWeekSun) 4 else 3
            DayOfWeek.FRIDAY -> if (firstDayOfWeekSun) 5 else 4
            DayOfWeek.SATURDAY -> if (firstDayOfWeekSun) 6 else 5
            DayOfWeek.SUNDAY -> if (firstDayOfWeekSun) 0 else 6
            else -> 0
        }

        val emptySpaces = List(emptySpacesForDaysList) { CustomCalendarDay() }
        val daysOfMonth = (1..selectedMonth.lengthOfMonth()).map { day ->
            CustomCalendarDay(
                day = day,
                month = selectedMonth.monthValue,
                year = selectedMonth.year,
                events = events.filter { it.eventDay == day && it.eventMonth == selectedMonth.monthValue && it.eventYear == selectedMonth.year }
            )
        }

        return emptySpaces + daysOfMonth
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

            if (day.events.isNotEmpty()) {
                holder.eventNotifier.visibility = View.VISIBLE
            }
            if (day == selectedDay){

                holder.dayTextView.setTextColor(selectedDayColor)



            }

            setBackgroundForToday(day, holder)

            holder.itemView.setOnClickListener {
                selectedDay = day
                calendarClickListener?.onDayClick(day)
                notifyDataSetChanged()
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
                val colorResource = todayBackgroundColor.getColorForState(intArrayOf(android.R.attr.color), Color.TRANSPARENT)
                val dayBackground =
                    if (todayDate == currentDate) colorResource else Color.TRANSPARENT
                holder.dayTextView.setBackgroundColor(dayBackground)
            }
        }

        inner class CalendarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val dayTextView: TextView = view.findViewById(R.id.dayCellText)
            val eventNotifier: TextView = view.findViewById(R.id.eventNotifier)

        }
    }


    interface CalendarClickListener {
        fun onDayClick(customCalendarDay: CustomCalendarDay)
        fun onPreviousMonthClick(previousYear: Int, previousMonth: Int)
        fun onNextMonthClick(nextYear: Int, nextMonth: Int)
        fun onCurrentMonthClick(currentYear: Int, currentMonth: Int)

    }

    private var calendarClickListener: CalendarClickListener? = null

    fun setOnCalendarClickListener(listener: CalendarClickListener) {
        this.calendarClickListener = listener
    }

}
