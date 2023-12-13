package com.example.rickmorty.presentation.features.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.R
import com.example.calendar.domain.entity.Event
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EventAdapter(
    private var events: List<Event>,
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_list_item, parent, false)
        return EventViewHolder(view)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.eventName.setText(event.name)
        holder.eventLocation.setText(event.location)
        holder.eventDateAdded.setText(convertMillisToFormattedDateTime(event.dateAdded))



//        initListeners(holder, event)
    }

//    private fun initListeners(holder: EventViewHolder, task: Task) {
//        holder.taskTitle.removeTextChangedListener(holder.textWatcher) // Remove the previous listener if any
//
//        val textWatcher = object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                if (areListenersActive) {
//                    taskCallback.updateTask(s.toString(), task)
//                }
//            }
//        }
//
//        holder.textWatcher = textWatcher // Save the TextWatcher instance in the ViewHolder
//        holder.taskTitle.addTextChangedListener(textWatcher)
//
//        holder.deleteButton.setOnClickListener {
//            taskCallback.deleteTask(task)
//        }
//
//        holder.completedCheckBox.setOnCheckedChangeListener { _, isChecked ->
//            if (areListenersActive) {
//                taskCallback.toggleCompletedStatus(isChecked, task)
//            }
//        }
//    }

//    fun removeTask(task: Task) {
//        if (tasks.contains(task)){
//            val taskPosition = tasks.indexOf(task)
//            tasks.removeAt(taskPosition)
//            notifyItemRemoved(taskPosition)
//        }
//    }

//    fun updateTasks(taskList: MutableList<Task>) {
//        tasks = taskList .filter { task ->  task.body.isNotEmpty() }.toMutableList()
//        notifyDataSetChanged()
//
//        recyclerView?.post {
//            val lastViewHolder =
//                recyclerView.findViewHolderForAdapterPosition(tasks.size - 1) as EventViewHolder?
//            lastViewHolder?.taskTitle?.clearFocus()
//        }
//
//    }

//    fun insertTask(task: Task) {
//        tasks.add(tasks.size, task)
//        notifyItemInserted(tasks.size)
//        moveCursorToNewTask()
//    }


    private fun convertMillisToFormattedDateTime(millis: Long): String {
        val dateFormat = SimpleDateFormat("M/d/yy, h:mm a", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = millis
        return dateFormat.format(calendar.time)
    }

    inner class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val eventName: EditText = view.findViewById(R.id.EventName)
        val eventLocation: EditText = view.findViewById(R.id.EventLocation)
        val eventDateAdded: EditText = view.findViewById(R.id.EventDateAdded)

    }

    fun updateEvents(newEvents: List<Event>) {
        events = newEvents
   notifyDataSetChanged()
    }
}
