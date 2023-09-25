package com.panther.events_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.panther.events_app.R
import com.panther.events_app.databinding.TimelineItemBinding
import com.panther.events_app.models.Items
import com.panther.events_app.models.events_model.EventResponseItem
import kotlin.random.Random

class TimelineAdapter(
    private val colorList: List<Int>
) : RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder>() {

    inner class TimelineViewHolder(val binding: TimelineItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<EventResponseItem>() {

        override fun areItemsTheSame(oldItem: EventResponseItem, newItem: EventResponseItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EventResponseItem, newItem: EventResponseItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TimelineAdapter.TimelineViewHolder {
        return TimelineViewHolder(TimelineItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: TimelineAdapter.TimelineViewHolder, position: Int) {
        val events = differ.currentList[position]

        holder.binding.apply {
            eventTypeTv.text = events.title
            dateTv.text = events.startDate
            timeTv.text = events.startTime
            locationTv.text = events.location
            durationTv.text = events.endDate
            imageView.setImageResource(R.drawable.image_emoji)
            val randomColorList = getRandomColor()
            cardview.setCardBackgroundColor(randomColorList)
        }
    }
    private fun getRandomColor(): Int {

        val randomColor = Random.nextInt(colorList.size)
        return colorList[randomColor]
    }




    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}