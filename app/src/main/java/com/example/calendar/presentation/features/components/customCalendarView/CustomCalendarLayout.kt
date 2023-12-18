package com.example.circularimageview.components

import android.content.ContentValues.TAG
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
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
import java.time.DayOfWeek
import java.time.LocalDate

data class CustomCalendarDay(
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
    private var daysWithEvents: List<CustomCalendarDay> = listOf()
    private var firstDayOfWeekSun: Boolean = true
    private var selectedDay: CustomCalendarDay? = null
    private var selectedDayColor: ColorStateList
    private var todayBackgroundColor: ColorStateList

    init {
        inflate(context, R.layout.custom_calendar_view, this)


        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomCalendarLayout)

        val headerBackgroundColor = getStyledColor(
            typedArray,
            R.styleable.CustomCalendarLayout_headerBackgroundColor,
            android.R.color.holo_blue_dark
        )
        val headerTextColor = getStyledColor(
            typedArray,
            R.styleable.CustomCalendarLayout_headerTextColor,
            android.R.color.white
        )
        val calendarBackgroundColor = getStyledColor(
            typedArray,
            R.styleable.CustomCalendarLayout_calendarBackgroundColor,
            android.R.color.system_tertiary_fixed_dim
        )
        val selectedDayTextColor = getStyledColor(
            typedArray,
            R.styleable.CustomCalendarLayout_selectedDayTextColor,
            android.R.color.holo_red_light
        )
        val currentDayTextColor = getStyledColor(
            typedArray,
            R.styleable.CustomCalendarLayout_currentDayBackgroundColor,
            android.R.color.darker_gray
        )

        val firstWeekDaySun =
            typedArray.getBoolean(R.styleable.CustomCalendarLayout_firstWeekDaySun, false)
        val previousMonthiconDrawable =
            typedArray.getResourceId(R.styleable.CustomCalendarLayout_previousMonthiconDrawable, 0)
        val nextMonthiconDrawable =
            typedArray.getResourceId(R.styleable.CustomCalendarLayout_nextMonthiconDrawable, 0)

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
            if (previousMonthiconDrawable != 0) {
                monthNavigationPrevious.setIconResource(previousMonthiconDrawable)
            } else monthNavigationPrevious.text = "<-"
            if (nextMonthiconDrawable != 0) {
                monthNavigationNext.setIconResource(nextMonthiconDrawable)
            } else monthNavigationNext.text = "->"
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

    fun setEvents(eventDays: List<CustomCalendarDay>) {
        daysWithEvents = eventDays
        adapter?.let {
            updateCalendarDaysWithEvents()
        }
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

    private fun updateCalendarDaysWithEvents() {
        val daysOfMonth = generateDaysOfMonthList(LocalDate.of(selectedYear, selectedMonth, 1))
        adapter.updateDaysOfTheMont(daysOfMonth)
    }

    private fun initListeners() {

        binding.monthNavigationPrevious.setOnClickListener() {
            displayPreviousMonth()
            calendarClickListenerInterface?.onPreviousMonthClick(selectedYear, selectedMonth)

        }

        binding.monthNavigationNext.setOnClickListener() {
            displayNextMonth()
            calendarClickListenerInterface?.onNextMonthClick(selectedYear, selectedMonth)
        }

        binding.monthTextView.setOnClickListener() {
            loadCurrentDate()
            calendarClickListenerInterface?.onCurrentMonthClick(selectedYear, selectedMonth)

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
            val matchingEvents =
                daysWithEvents.filter { it.day == day && it.month == selectedMonth.monthValue && it.year == selectedMonth.year }
            CustomCalendarDay(
                day = day,
                month = selectedMonth.monthValue,
                year = selectedMonth.year,
                events = matchingEvents.flatMap { it.events }
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
            if (day == selectedDay) {

                holder.dayTextView.setTextColor(selectedDayColor)
            }

            setBackgroundForToday(day, holder)

            holder.itemView.setOnClickListener {
                selectedDay = day
                calendarClickListenerInterface?.onDayClick(day)

                Log.d(TAG, "day presed: $day ")
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
                val colorResource = todayBackgroundColor.getColorForState(
                    intArrayOf(android.R.attr.color),
                    Color.TRANSPARENT
                )
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


    interface CalendarClickListenerInterface {
        fun onDayClick(customCalendarDay: CustomCalendarDay)
        fun onPreviousMonthClick(previousYear: Int, previousMonth: Int)
        fun onNextMonthClick(nextYear: Int, nextMonth: Int)
        fun onCurrentMonthClick(currentYear: Int, currentMonth: Int)
    }

    private var calendarClickListenerInterface: CalendarClickListenerInterface? = null

    fun setOnCalendarClickListener(listener: CalendarClickListenerInterface) {
        this.calendarClickListenerInterface = listener
    }

}
