package com.panther.events_app.api

import com.panther.events_app.models.Events
import com.panther.events_app.models.LoginResponse
import com.panther.events_app.models.group_event_model.CommentsResponse
import com.panther.events_app.models.group_event_model.CommentsResponseItem
import com.panther.events_app.models.group_event_model.EventsResponse
import com.panther.events_app.models.group_event_model.EventsResponseItem
import com.panther.events_app.models.group_event_model.PostCommentBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EventsApi {

    suspend fun getEventsResponse() : Response<Events>

    @GET("login")
    suspend fun authenticateUser() : Response<LoginResponse>

    @GET("events")
    suspend fun getAllGroupEvents() : Response<EventsResponse>

    @GET("events/{id}")
    suspend fun getGroupEventInfo(
        @Path("id") id:String
    ): Response<EventsResponseItem>


    @GET("comments")
    suspend fun getGroupEventComments(): Response<CommentsResponse>//docs doesn't return anything but should return a list of comments

    @POST("comments")
    suspend fun postGroupEventComment(
        @Body comment: PostCommentBody
    ): Response<CommentsResponseItem>//docs doesn't return anything

}