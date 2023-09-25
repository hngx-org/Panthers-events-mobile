package com.panther.events_app

import android.os.Build
import androidx.annotation.RequiresApi
import com.panther.events_app.models.EventComments
import com.panther.events_app.models.MyPeopleEvent
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale


const val CURRENT_DESTINATION_ID = "current destination ID"
const val BASE_URL = "https://octopus-app-nax2o.ondigitalocean.app/api/"

const val EVENTS_PREFERENCE = "Events shared preference"
const val SESSION_TOKEN = "Auth session token"


fun getMyPeopleEventList():List<MyPeopleEvent>{
    val myPeopleEventList = mutableListOf<MyPeopleEvent>()
    repeat(8){
        val event = MyPeopleEvent(
            title = "Football game",
            location = "Camp Nou",
            duration = "Friday 4 - 6pm",
            date = "May ${(1..30).random()}",
            commentsCount = (1..100).random().toString()
        )
        myPeopleEventList.add(event)
    }
    return myPeopleEventList
}

fun getMyPeopleEventComments():List<EventComments>{
    val commentsList = mutableListOf<EventComments>()
    repeat(5){
        val event = EventComments(
            comment = "I will be there, no matter what",
            image = null
        )
        commentsList.add(event)
    }
    commentsList.add(
        EventComments(
        comment = "I defo won’t miss this",
        image = R.drawable.comment_image_1
    )
    )
    commentsList.add(
        EventComments(
        comment = "Me too",
        image = R.drawable.comment_image_1
    )
    )

    return commentsList
}

@RequiresApi(Build.VERSION_CODES.O)
fun getDate(date:String?):String{
    return if (date != null){
        val format =
            DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val localDate = LocalDate.parse(date, format)
        val dateFormatter = DateTimeFormatter.ofPattern("MMM dd", Locale.getDefault())
        localDate.format(dateFormatter)
    }else{
        "Date"
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getDuration(startDate: String?, endDate:String?): String {
    var newStartDate = ""
    var newEndDate = ""

    if (startDate.isNullOrEmpty()){
        newStartDate ="No Date"
    }

    if (endDate.isNullOrEmpty()){
        newEndDate ="No Date"
    }

    if (startDate != null) {
        val format =
            DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val localDate = LocalDate.parse(startDate, format)


        val calender = Calendar.getInstance()
        val splitDate = localDate.toString().split("-")
        calender.set(splitDate[0].toInt(),splitDate[1].toInt(),splitDate[2].toInt())
        newStartDate = checkWeekdayName(calender[Calendar.DAY_OF_WEEK])
    }
    if (endDate != null) {
        val format =
            DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val localDate = LocalDate.parse(endDate, format)


        val calender = Calendar.getInstance()
        val splitDate = localDate.toString().split("-")
        calender.set(splitDate[0].toInt(),splitDate[1].toInt(),splitDate[2].toInt())
        newEndDate = checkWeekdayName(calender[Calendar.DAY_OF_WEEK])
    }

    return if (newStartDate == newEndDate) {
        newStartDate
    } else {
        "$newStartDate--$newEndDate"
    }
}

private fun checkWeekdayName(day: Int):String {
    return when (day) {
        1 -> "Monday"
        2 -> "Tuesday"
        3 -> "Wednesday"
        4 -> "Thursday"
        5 -> "Friday"
        6 -> "Saturday"
        else -> "Sunday"
    }
}

