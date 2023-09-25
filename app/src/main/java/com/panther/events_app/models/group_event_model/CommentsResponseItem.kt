package com.panther.events_app.models.group_event_model

import com.google.gson.annotations.SerializedName


data class CommentsResponseItem(
    @SerializedName("body")
    val body: String?="",
    @SerializedName("created_at")
    val createdAt: String="",
    @SerializedName("event")
    val event: String="",
    @SerializedName("id")
    val id: String="",
    @SerializedName("updated_at")
    val updatedAt: String="",
    @SerializedName("user")
    val user: String=""
)