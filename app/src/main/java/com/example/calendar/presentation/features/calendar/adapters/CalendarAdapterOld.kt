//package com.example.calendar.presentation.features.calendar.adapters
//
//import android.graphics.Color
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.calendar.R
//import com.example.calendar.domain.entity.Day
//import com.example.calendar.presentation.features.calendar.CalendarCallbackInterface
//import java.time.LocalDate
//
//
//class CalendarAdapterOld(
//    private var days: List<Day>,
//    private val calendarCallbacks: CalendarCallbackInterface
//) : RecyclerView.Adapter<CalendarAdapterOld.CalendarViewHolder>() {
////
////
////    inner class CalendarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
////        val dayTextView: TextView = view.findViewById(R.id.dayCellText)
////
////        init {
////            itemView.setOnClickListener {
////                val position = adapterPosition
////                if (position != RecyclerView.NO_POSITION) {
////                    val clickedDay = days[position]
////                    calendarCallbacks.daySelected(clickedDay)
////                }
////            }
////        }
////    }
////
////
////    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
////        val view = LayoutInflater.from(parent.context)
////            .inflate(R.layout.calendar_cell, parent, false)
////        return CalendarViewHolder(view)
////    }
////
////    override fun getItemCount(): Int {
////        return days.size
////    }
////
////    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
////        val day = days[position]
////        val dayString = if (day.day == 0) "" else day.day.toString()
////
////        holder.dayTextView.text = dayString
////
////        setBackgroundForToday(day, holder)
////
////    }
////
////
////    private fun setBackgroundForToday(day: Day, holder: CalendarViewHolder){
////
////        if (day.day > 0 && day.month > 0 && day.year > 0) {
////            val currentDate = LocalDate.now()
////            val todayDate = LocalDate.of(day.year, day.month, day.day)
////            val dayBackground = if(todayDate == currentDate) Color.RED else Color.TRANSPARENT
////            holder.dayTextView.setBackgroundColor(dayBackground)
////        }
////    }
////
////
////    fun updateDaysOfTheMont(listOfDays: List<Day>) {
////        days = listOfDays
////        notifyDataSetChanged()
////    }
//}
