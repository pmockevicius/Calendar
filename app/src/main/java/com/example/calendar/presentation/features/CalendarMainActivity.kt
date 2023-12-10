package com.example.calendar.presentation.features

import CalendarAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.R

class CalendarMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initAdapter()

    }

    private fun initAdapter() {
        val recyclerView: RecyclerView = findViewById(R.id.calendarRV)
        val layoutManager = GridLayoutManager(this, 7)
        recyclerView.layoutManager = layoutManager

        val days = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20)
        val adapter = CalendarAdapter(days)
        recyclerView.adapter = adapter

    }

}