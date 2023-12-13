package com.example.calendar.presentation.features.calendar

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.calendar.R
import com.example.calendar.databinding.ActivityMainBinding
import com.example.calendar.domain.entity.Event
import com.example.circularimageview.components.CustomCalendarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


@AndroidEntryPoint
class EventMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private  lateinit var customCalendarLayout: CustomCalendarLayout
    private lateinit var viewModel: EventViewModel


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
        customCalendarLayout.setEvents(listOf(2,3,4))
        initListeners()

        addEvents()

    }

    private fun addEvents() {
                viewModel.addEvent(Event(
            name = "testName",
            eventDay = 15,
            eventMonth = 10,
            eventYear = 2023,
            dateAdded = System.currentTimeMillis(),
            location = "testLocation"))
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


        customCalendarLayout.setOnCalendarClickListener(object :
            CustomCalendarLayout.CalendarClickListener {
            override fun onDayClick(selectedDay: String) {
                Log.d(TAG, "onDayClicked: $selectedDay")
            }

            override fun onPreviousMonthClick(previousYear: Int, previousMonth: Int) {
                Log.d(TAG, "onPreviousMonthClick: $previousYear $previousMonth")
                viewModel.getDaysWithEvents( previousYear, previousMonth)
            }

            override fun onNextMonthClick(nextYear: Int, nextMonth: Int) {
                viewModel.getDaysWithEvents( nextYear, nextMonth)
            }
        })
    }



}