package com.panther.events_app.bottom_nav

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.panther.events_app.MyPeopleEvent
import com.panther.events_app.R
import com.panther.events_app.databinding.BottomNavViewholderBinding

class BottomNavAdapter() : ListAdapter<BottomNavItem, BottomNavAdapter.ViewHolder>(
    diffObject
) {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = BottomNavViewholderBinding.bind(view)

        fun bind(navItem: BottomNavItem) {

            binding.apply {
                val backgroundColor = if (navItem.isSelected) {
                    ContextCompat.getColor(itemView.context, R.color.some_yellow)
                } else {
                    ContextCompat.getColor(itemView.context, R.color.white)
                }


                colorBackgroundHolder.setBackgroundColor(backgroundColor)
                navItemIcon.setImageResource(navItem.icon)
                navItemIcon.setOnClickListener {
                    listener?.let { it(navItem.title) }
                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.bottom_nav_viewholder, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = getItem(position)
        if (pos != null)
            holder.bind(pos)

    }

    companion object {
        val diffObject = object : DiffUtil.ItemCallback<BottomNavItem>() {
            override fun areItemsTheSame(oldItem: BottomNavItem, newItem: BottomNavItem): Boolean {
                return oldItem.icon == newItem.icon
            }

            override fun areContentsTheSame(
                oldItem: BottomNavItem,
                newItem: BottomNavItem
            ): Boolean {
                return oldItem.icon == newItem.icon && oldItem.isSelected == newItem.isSelected
            }
        }
    }


    private var listener: ((Int) -> Unit)? = null

    fun adapterClickListener(listener: (Int) -> Unit) {
        this.listener = listener
    }

}
