package com.panther.events_app.fragment.events.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.panther.events_app.models.EventComments
import com.panther.events_app.R
import com.panther.events_app.databinding.EventCommentsViewholderBinding
import com.panther.events_app.models.group_event_model.CommentImageResponseItem

class EventCommentsAdapter() : ListAdapter<CommentImageResponseItem, EventCommentsAdapter.ViewHolder>(
    diffObject
) {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = EventCommentsViewholderBinding.bind(view)

        fun bind(comment: CommentImageResponseItem) {

            binding.apply {
                if (comment.url.isEmpty()) {
                    commentImage.visibility = View.GONE
                }else {
                    commentImage.visibility = View.VISIBLE
                    commentImage.load(comment.url){
                        placeholder(R.drawable.image_icon)
                        error(R.drawable.image_icon)
                    }
                }


                eventCommentText.text = "comment here"
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
        val diffObject = object : DiffUtil.ItemCallback<CommentImageResponseItem>() {
            override fun areItemsTheSame(oldItem: CommentImageResponseItem, newItem: CommentImageResponseItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CommentImageResponseItem, newItem: CommentImageResponseItem): Boolean {
                return oldItem.id == newItem.id && oldItem.url == newItem.url
            }
        }
    }



}
