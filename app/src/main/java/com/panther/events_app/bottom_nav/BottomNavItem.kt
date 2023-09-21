package com.panther.events_app.bottom_nav

import com.panther.events_app.R

data class BottomNavItem(
    val title:Int=0,
    val icon:Int=0,
    val isSelected:Boolean=false
){
    companion object {
        fun getIcon(id:Int):Int{
            return when(id){
                R.id.eventInfo -> R.drawable.location
                else-> R.drawable.time_duration
            }
        }
    }
}
