package com.panther.events_app.fragment.events

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.panther.events_app.EventComments
import com.panther.events_app.R
import com.panther.events_app.databinding.EventCommentsViewholderBinding

class EventCommentsAdapter() : ListAdapter<EventComments, EventCommentsAdapter.ViewHolder>(
    diffObject
) {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = EventCommentsViewholderBinding.bind(view)

        fun bind(comment: EventComments) {

                if (comment.image == null){
                    binding.commentImage.visibility = View.GONE
                }
                binding.commentImage.visibility = View.VISIBLE
                comment.image?.let {
                    binding.commentImage.setImageResource(it)
                }
                binding.eventCommentText.text = comment.comment

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
