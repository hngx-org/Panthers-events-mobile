package com.panther.events_app.models.group_event_model

import com.google.gson.annotations.SerializedName


data class PostCommentBody(
    @SerializedName("body")
    val body: String,
    @SerializedName("event")
    val event: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("user")
    val user: String
)