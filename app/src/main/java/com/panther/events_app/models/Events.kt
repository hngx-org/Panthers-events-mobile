package com.panther.events_app.models


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Events(
    @SerializedName("group-images")
    val groupImages: String?,
    @SerializedName("groupevents")
    val groupevents: String?,
    @SerializedName("groups")
    val groups: String?,
    @SerializedName("images")
    val images: String?,
    @SerializedName("usergroups")
    val usergroups: String?
) : Parcelable