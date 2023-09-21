package com.panther.events_app

data class MyPeopleEvent(
    val title:String,
    val location:String,
    val duration:String,
    val date:String,
    val commentsCount:String,

    )

data class EventComments(
    val comment:String,
    val image:Int?
)



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
    commentsList.add(EventComments(
        comment = "I defo won’t miss this",
        image = R.drawable.comment_image_1
    ))
    commentsList.add(EventComments(
        comment = "Me too",
        image = R.drawable.comment_image_1
    ))

    return commentsList
}

