package com.panther.events_app.fragment.events.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.panther.events_app.R
import com.panther.events_app.databinding.TimelineEventViewholderBinding
import com.panther.events_app.getDate
import com.panther.events_app.models.group_event_model.EventsResponseItem
import com.panther.events_app.getDuration
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
class MyPeopleEventAdapter() : ListAdapter<EventsResponseItem, MyPeopleEventAdapter.ViewHolder>(
    diffObject
) {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = TimelineEventViewholderBinding.bind(view)


        fun bind(event: EventsResponseItem) {
            binding.apply {
                eventTitleText.text = event.title.ifEmpty { "--- ---" }
                eventLocationText.text = event.location.ifEmpty { "--- ---" }
                eventDurationText.text = getDuration(event.startDate, event.endDate)
                eventDateText.text = getDate(event.startDate)
                image1.clipToOutline = true
                image2.clipToOutline = true
                image3.clipToOutline = true
                eventCommentsText.setOnClickListener {
                    listener?.let { it(event) }
                }
//                eventCommentsText.text = "${ event.commentCount } comments"
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
        val diffObject = object : DiffUtil.ItemCallback<EventsResponseItem>() {
            override fun areItemsTheSame(
                oldItem: EventsResponseItem,
                newItem: EventsResponseItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: EventsResponseItem,
                newItem: EventsResponseItem
            ): Boolean {
                return oldItem.id == newItem.id && oldItem.title == newItem.title
            }
        }
    }

    private var listener: ((EventsResponseItem) -> Unit)? = null

    fun adapterClickListener(listener: (EventsResponseItem) -> Unit) {
        this.listener = listener
    }


}
