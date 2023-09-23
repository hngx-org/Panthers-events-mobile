package com.panther.events_app.models.group_event_model


import com.google.gson.annotations.SerializedName


data class PostGroupEventResponse(
    @SerializedName("event")
    val event: String,
    @SerializedName("group")
    val group: String,
    @SerializedName("id")
    val id: Int
)