package com.panther.events_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.panther.events_app.R
import com.panther.events_app.databinding.TimelineItemBinding
import com.panther.events_app.models.Items
import kotlin.random.Random

class TimelineAdapter(
    private val itemList: List<Items>,
    private val colorList: List<Int>) : RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder>() {

    inner class TimelineViewHolder(val binding: TimelineItemBinding) : RecyclerView.ViewHolder(binding.root)

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
        val currentItem = itemList[position]

        holder.binding.apply {
            eventTypeTv.text = currentItem.event
            dateTv.text = currentItem.date
            timeTv.text = currentItem.time
            locationTv.text = currentItem.location
            durationTv.text = currentItem.duration
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
        return itemList.size
    }

}