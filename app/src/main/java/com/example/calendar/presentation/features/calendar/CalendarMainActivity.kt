package com.example.calendar.presentation.features.calendar

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.calendar.R
import com.example.calendar.databinding.ActivityMainBinding
import com.example.calendar.domain.entity.Day
import com.example.calendar.presentation.features.events.EventActivity
import com.example.circularimageview.components.CustomCalendarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CalendarMainActivity : AppCompatActivity(), CustomCalendarLayout.OnDayClickListener {

    private lateinit var viewModel: CalendarViewModelInterface

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[CalendarViewModel::class.java]

        initView()
    }

    private fun initView() {
        initObservers()
        initListeners()
        viewModel.loadCurrentMonth()

    }

    private fun initListeners() {

        val customCalendarLayout: CustomCalendarLayout = findViewById(R.id.myCalendar)

        customCalendarLayout.setOnDayClickListener(object :
            CustomCalendarLayout.OnDayClickListener {
            override fun onDayClick(selectedDay: String) {
                Log.d(TAG, "onDayClicked: $selectedDay")
            }
        })
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.map { it.days }
                    .distinctUntilChanged().collect {
//                        daysOfMonthAdapter.updateDaysOfTheMont(it)
                    }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.map { it.monthYearText }
                    .distinctUntilChanged().collect {
//                       binding.monthTextView.setText(it)
                    }
            }
        }

    }

//    private fun initAdapter() {
//        val recyclerView: RecyclerView = findViewById(R.id.calendarRV)
//        val layoutManager = GridLayoutManager(this, 7)
//        recyclerView.layoutManager = layoutManager
//
//        val days = emptyList<Day>()
//        daysOfMonthAdapter = CalendarAdapterOld(days, CalendarCallbacks())
//        recyclerView.adapter = daysOfMonthAdapter
//
//    }

    private inner class CalendarCallbacks : CalendarCallbackInterface {
        override fun daySelected(day: Day) {
            if (day.day != 0) {
                navigateToEventsActivity()
            }
        }

    }


    fun navigateToEventsActivity() {
        val intent = Intent(
            this, EventActivity::class.java
        )
        startActivity(intent)
    }

    override fun onDayClick(selectedDay: String) {
        Log.d(TAG, "onDayClick: $selectedDay ")
        println("$selectedDay")
    }

}