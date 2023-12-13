package com.example.calendar.presentation.features.events

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calendar.R
import com.example.calendar.databinding.ActivityMainBinding
import com.example.calendar.domain.entity.Event
import com.example.circularimageview.components.CustomCalendarLayout
import com.example.rickmorty.presentation.features.details.EventAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate


@AndroidEntryPoint
class EventMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var customCalendarLayout: CustomCalendarLayout
    private lateinit var viewModel: EventViewModel
    private lateinit var eventAdapter: EventAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[EventViewModel::class.java]
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        customCalendarLayout = findViewById(R.id.myCalendar)
        viewModel.loadCurrentMonth()
        initListeners()
        setUpTaskAdapter()
        val currentDate = LocalDate.now()
        setEventDayText(currentDate.year, currentDate.monthValue, currentDate.dayOfMonth)


//        addEvents()


    }

    private fun setUpTaskAdapter() {
        val recyclerView = binding.eventsRV
        eventAdapter = EventAdapter(listOf())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = eventAdapter
    }

    private fun addEvents() {
        viewModel.addEvent(
            Event(
                name = "Lunch",
                eventDay = 10,
                eventMonth = 1,
                eventYear = 2024,
                dateAdded = System.currentTimeMillis(),
                location = "restaurant"
            )
        )
    }

    private fun initListeners() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.map { it.daysWithEvents }
                    .distinctUntilChanged().collect {
                        customCalendarLayout.setEvents(it)
                    }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.map { it.events }
                    .distinctUntilChanged().collect {
                        eventAdapter.updateEvents(it)
                    }
            }
        }

        

        customCalendarLayout.setOnCalendarClickListener(object :
            CustomCalendarLayout.CalendarClickListener {
            override fun onDayClick(selectedYear: Int, selectedMonth: Int, selectedDay: Int) {
                setEventDayText(selectedYear, selectedMonth, selectedDay)
               viewModel.getEventsFor(selectedYear, selectedMonth, selectedDay)
            }

            override fun onPreviousMonthClick(previousYear: Int, previousMonth: Int) {
                viewModel.getDaysWithEvents(previousYear, previousMonth)
            }

            override fun onNextMonthClick(nextYear: Int, nextMonth: Int) {
                viewModel.getDaysWithEvents(nextYear, nextMonth)
            }

            override fun onCurrentMonthClick(currentYear: Int, currentMonth: Int) {
                viewModel.getDaysWithEvents(currentYear, currentMonth)
            }
        })
    }

    fun setEventDayText(year: Int, month: Int, day: Int){
        binding.eventsDay.setText("Events for $day/$month/$year")
    }


}