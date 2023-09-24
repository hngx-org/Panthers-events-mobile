package com.panther.events_app.models.events_model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class EventResponseItem(
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("creator")
    val creator: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("end_date")
    val endDate: String?,
    @SerializedName("end_time")
    val endTime: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("start_date")
    val startDate: String?,
    @SerializedName("start_time")
    val startTime: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
) : Parcelable