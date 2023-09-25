package com.panther.events_app.api

import android.content.Context
import android.content.SharedPreferences
import com.panther.events_app.EVENTS_PREFERENCE
import com.panther.events_app.SESSION_TOKEN

class EventsSharedPreference {
    private val editor = sharedPref?.edit()

    fun updateSharedPref(token: String) {
        editor?.putString(SESSION_TOKEN, token)?.apply()
    }

    fun getSharedPref(): String {
        return sharedPref?.getString(SESSION_TOKEN,"")?:""
    }

    companion object{
        private var sharedPref:SharedPreferences? = null
        fun initSharedPref(context: Context){
            sharedPref = context.getSharedPreferences(EVENTS_PREFERENCE, Context.MODE_PRIVATE)
        }
    }

}