package com.panther.events_app.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("success")
    val success:Boolean=false,
    @SerializedName("user_id")
    val user_id:String="",
    @SerializedName("session_token")
    val session_token:String="",
    @SerializedName("status")
    val status:Int=0
)
