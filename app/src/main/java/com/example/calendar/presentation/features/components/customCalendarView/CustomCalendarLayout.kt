package com.example.circularimageview.components

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
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

data class CustomCalendarDayWithEvents(
    val day: Int = 0,
    val month: Int = 0,
    val year: Int = 0,
    val events: List<Any> = listOf(),
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
    private var events: List<Any> = listOf()
    private var firstDayOfWeekSun: Boolean = true
    private var selectedDay: CustomCalendarDayWithEvents? = null
    private var selectedDayColor: ColorStateList
    private lateinit var todayBackgroundColor: ColorStateList

    init {
        inflate(context, R.layout.custom_calendar_view, this)


        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomCalendarLayout)

        val headerBackgroundColor = getStyledColor(typedArray, R.styleable.CustomCalendarLayout_headerBackgroundColor, android.R.color.transparent)
        val headerTextColor = getStyledColor(typedArray, R.styleable.CustomCalendarLayout_headerTextColor, android.R.color.transparent)
        val calendarBackgroundColor = getStyledColor(typedArray, R.styleable.CustomCalendarLayout_calendarBackgroundColor, android.R.color.transparent)
        val selectedDayTextColor = getStyledColor(typedArray, R.styleable.CustomCalendarLayout_selectedDayTextColor, android.R.color.transparent)
        val currentDayTextColor = getStyledColor(typedArray, R.styleable.CustomCalendarLayout_currentDayBackgroundColor, android.R.color.transparent)

        val firstWeekDaySun = typedArray.getBoolean(R.styleable.CustomCalendarLayout_firstWeekDaySun, false)

        typedArray.recycle()

        with(binding) {
            header.setBackgroundColor(headerBackgroundColor)
            monthTextView.setTextColor(headerTextColor)
            monthNavigationNext.setTextColor(headerTextColor)
            monthNavigationPrevious.setTextColor(headerTextColor)
            calendarWrapper.setBackgroundColor(calendarBackgroundColor)
            val iconTint = ColorStateList.valueOf(headerTextColor)
            monthNavigationNext.iconTint = iconTint
            monthNavigationPrevious.iconTint = iconTint
            selectedDayColor = ColorStateList.valueOf(selectedDayTextColor)
            todayBackgroundColor = ColorStateList.valueOf(currentDayTextColor)
        }

        configureWeekdaysView(firstWeekDaySun)

        recyclerView = binding.calendarRV
        val layoutManager = GridLayoutManager(context, 7)
        recyclerView.layoutManager = layoutManager

        adapter = CustomCalendarAdapter(listOf())
        recyclerView.adapter = adapter

        loadCurrentDate()
        initListeners()
    }

    private fun configureWeekdaysView(firstWeekDaySun: Boolean) {
        with(binding) {
            if (firstWeekDaySun) {
                firstDayOfWeekSun = true
                weekdaysSunFirst.visibility = View.VISIBLE
                weekdaysMonFirst.visibility = View.GONE
            } else {
                firstDayOfWeekSun = false
                weekdaysSunFirst.visibility = View.GONE
                weekdaysMonFirst.visibility = View.VISIBLE
            }
        }
    }


    private fun getStyledColor(typedArray: TypedArray, index: Int, defaultColor: Int): Int {
        return typedArray.getColor(index, ContextCompat.getColor(context, defaultColor))
    }
    fun setEvents(daysWithEvents: List<CustomCalendarDayWithEvents>) {
        daysWithEvents = daysWithEvents
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
    ): List<CustomCalendarDayWithEvents> {

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

        val emptySpaces = List(emptySpacesForDaysList) { CustomCalendarDayWithEvents() }
        val daysOfMonth = (1..selectedMonth.lengthOfMonth()).map { day ->
            CustomCalendarDayWithEvents(
                day = day,
                month = selectedMonth.monthValue,
                year = selectedMonth.year,
                events = events.filter { it.eventDay == day && it.eventMonth == selectedMonth.monthValue && it.eventYear == selectedMonth.year }
            )
        }

        return emptySpaces + daysOfMonth
    }

    inner class CustomCalendarAdapter(
        private var days: List<CustomCalendarDayWithEvents>,
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

        fun updateDaysOfTheMont(listOfDays: List<CustomCalendarDayWithEvents>) {
            days = listOfDays
            notifyDataSetChanged()
        }

        private fun setBackgroundForToday(day: CustomCalendarDayWithEvents, holder: CalendarViewHolder) {
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
        fun onDayClick(customCalendarDayWithEvents: CustomCalendarDayWithEvents)
        fun onPreviousMonthClick(previousYear: Int, previousMonth: Int)
        fun onNextMonthClick(nextYear: Int, nextMonth: Int)
        fun onCurrentMonthClick(currentYear: Int, currentMonth: Int)

    }

    private var calendarClickListener: CalendarClickListener? = null

    fun setOnCalendarClickListener(listener: CalendarClickListener) {
        this.calendarClickListener = listener
    }

}
