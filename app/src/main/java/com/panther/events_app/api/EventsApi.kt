package com.panther.events_app.api

import com.panther.events_app.models.Events
import retrofit2.Response

interface EventsApi {

    suspend fun getEventsResponse() : Response<Events>

}