package com.panther.events_app.fragment.events.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.panther.events_app.models.MyPeopleEvent
import com.panther.events_app.R
import com.panther.events_app.databinding.TimelineEventViewholderBinding

class MyPeopleEventAdapter() : ListAdapter<MyPeopleEvent, MyPeopleEventAdapter.ViewHolder>(
    diffObject
) {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = TimelineEventViewholderBinding.bind(view)

        fun bind(event: MyPeopleEvent) {
            binding.apply {
               eventTitleText.text = event.title
               eventLocationText.text = event.location
               eventDurationText.text = event.duration
               eventDateText.text = event.date
               image1.clipToOutline = true
               image2.clipToOutline = true
               image3.clipToOutline = true
               eventCommentsText.setOnClickListener {
                    listener?.let { it(event) }
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.timeline_event_viewholder, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = getItem(position)
        if (pos != null)
            holder.bind(pos)

    }

    companion object {
        val diffObject = object : DiffUtil.ItemCallback<MyPeopleEvent>() {
            override fun areItemsTheSame(oldItem: MyPeopleEvent, newItem: MyPeopleEvent): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(
                oldItem: MyPeopleEvent,
                newItem: MyPeopleEvent
            ): Boolean {
                return oldItem.title == newItem.title && oldItem.date == newItem.date
            }
        }
    }

    private var listener: ((MyPeopleEvent) -> Unit)? = null

    fun adapterClickListener(listener: (MyPeopleEvent) -> Unit) {
        this.listener = listener
    }


}
