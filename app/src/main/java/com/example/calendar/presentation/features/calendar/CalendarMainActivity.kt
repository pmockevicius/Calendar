package com.example.calendar.presentation.features.calendar

import com.example.calendar.presentation.features.calendar.adapters.CalendarAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.R
import com.example.calendar.databinding.ActivityMainBinding
import com.example.calendar.domain.entity.Day
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CalendarMainActivity : AppCompatActivity() {

    private lateinit var viewModel: CalendarViewModelInterface

        private lateinit var binding: ActivityMainBinding
    private lateinit var daysOfMonthAdapter: CalendarAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[CalendarViewModel::class.java]



        initView()
    }

    private fun initView() {
        initAdapter()
        initObservers()
        initListeners()
        viewModel.loadCurrentMonth()
    }

    private fun initListeners() {
        binding.monthNavigationPrevious.setOnClickListener(){
            viewModel.displayPreviousMonth()
        }

        binding.monthNavigationNext.setOnClickListener(){
            viewModel.displayNextMonth()
        }

        binding.monthTextView.setOnClickListener(){
            viewModel.loadCurrentMonth()
            Toast.makeText(this, "Displaying current date", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.map { it.days }
                    .distinctUntilChanged().collect {
                        daysOfMonthAdapter.updateDaysOfTheMont(it)
                    }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.map { it.monthYearText }
                    .distinctUntilChanged().collect {
                       binding.monthTextView.setText(it)
                    }
            }
        }

    }

    private fun initAdapter() {
        val recyclerView: RecyclerView = findViewById(R.id.calendarRV)
        val layoutManager = GridLayoutManager(this, 7)
        recyclerView.layoutManager = layoutManager

        val days = emptyList<Day>()
        daysOfMonthAdapter = CalendarAdapter(days)
        recyclerView.adapter = daysOfMonthAdapter

    }

}