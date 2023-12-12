package com.example.calendar.presentation.features.events

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.calendar.databinding.ActivityEventBinding

class EventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}