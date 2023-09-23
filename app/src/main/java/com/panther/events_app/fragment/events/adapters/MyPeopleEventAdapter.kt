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
import com.panther.events_app.models.group_event_model.GroupEventResponseItem

class MyPeopleEventAdapter() : ListAdapter<GroupEventResponseItem, MyPeopleEventAdapter.ViewHolder>(
    diffObject
) {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = TimelineEventViewholderBinding.bind(view)

        fun bind(event: GroupEventResponseItem) {
            binding.apply {
               eventTitleText.text = event.event
//               eventLocationText.text = event.location
//               eventDurationText.text = event.duration
//               eventDateText.text = event.date
               image1.clipToOutline = true
               image2.clipToOutline = true
               image3.clipToOutline = true
               eventCommentsText.setOnClickListener {
                    listener?.let { it(event) }
                }
                eventCommentsText.text = "${ event.commentCount } comments"
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
        val diffObject = object : DiffUtil.ItemCallback<GroupEventResponseItem>() {
            override fun areItemsTheSame(oldItem: GroupEventResponseItem, newItem: GroupEventResponseItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: GroupEventResponseItem,
                newItem: GroupEventResponseItem
            ): Boolean {
                return oldItem.id == newItem.id && oldItem.event == newItem.event
            }
        }
    }

    private var listener: ((GroupEventResponseItem) -> Unit)? = null

    fun adapterClickListener(listener: (GroupEventResponseItem) -> Unit) {
        this.listener = listener
    }


}
