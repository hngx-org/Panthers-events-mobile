package com.panther.events_app.fragment.events.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.panther.events_app.models.EventComments
import com.panther.events_app.R
import com.panther.events_app.databinding.EventCommentsViewholderBinding

class EventCommentsAdapter() : ListAdapter<EventComments, EventCommentsAdapter.ViewHolder>(
    diffObject
) {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = EventCommentsViewholderBinding.bind(view)

        fun bind(comment: EventComments) {

            binding.apply {
                if (comment.image == null) {
                    commentImage.visibility = View.GONE
                }
                commentImage.visibility = View.VISIBLE
                comment.image?.let {
                    commentImage.setImageResource(it)
                }
                eventCommentText.text = comment.comment
                profileImg.clipToOutline = true
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.event_comments_viewholder, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = getItem(position)
        if (pos != null)
            holder.bind(pos)

    }

    companion object {
        val diffObject = object : DiffUtil.ItemCallback<EventComments>() {
            override fun areItemsTheSame(oldItem: EventComments, newItem: EventComments): Boolean {
                return oldItem.comment == newItem.comment
            }

            override fun areContentsTheSame(oldItem: EventComments, newItem: EventComments): Boolean {
                return oldItem.comment == newItem.comment && oldItem.image == newItem.image
            }
        }
    }



}
