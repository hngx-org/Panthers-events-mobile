package com.panther.events_app.api

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.panther.events_app.EVENTS_PREFERENCE
import com.panther.events_app.SESSION_TOKEN

class EventsSharedPreference {
    private val editor = sharedPref?.edit()

    fun updateSharedPref(token: String) {
        Log.d("AUTH TOKEN", "updateSharedPref: $token ")
        editor?.putString(SESSION_TOKEN, token)?.apply()
    }

    fun getSharedPref(): String {
        val token = sharedPref?.getString(SESSION_TOKEN,"")?:""
        Log.d("AUTH TOKEN", "getSharedPref: $token")
        return token
    }
    fun updateSharedPrefUser(token: String) {
        Log.d("AUTH TOKEN", "userPref: $token ")
        editor?.putString("USER", token)?.apply()
    }

    fun getSharedPrefUser(): String {
        val token = sharedPref?.getString("USER", "") ?: ""
        Log.d("AUTH TOKEN", "userPref: $token")
        return token
    }

    companion object{
        private var sharedPref:SharedPreferences? = null
        fun initSharedPref(context: Context){
            sharedPref = context.getSharedPreferences(EVENTS_PREFERENCE, Context.MODE_PRIVATE)
        }
    }

}