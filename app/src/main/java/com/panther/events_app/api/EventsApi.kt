package com.panther.events_app.api

import com.panther.events_app.models.Events
import com.panther.events_app.models.LoginResponse
import com.panther.events_app.models.group_event_model.CommentImageResponse
import com.panther.events_app.models.group_event_model.CommentsResponse
import com.panther.events_app.models.group_event_model.EventsResponse
import com.panther.events_app.models.group_event_model.EventsResponseItem
import com.panther.events_app.models.group_event_model.GroupEventResponseItem
import com.panther.events_app.models.group_event_model.PostCommentImage
import retrofit2.Response
import retrofit2.http.DELETE
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

    @DELETE("groupevents/{id}")
    suspend fun deleteGroupEvent(
        @Path("id") id: String
    ): Response<GroupEventResponseItem>//docs doesn't return anything

    @GET("comments")
    suspend fun getGroupEventComments(): Response<CommentsResponse>//docs doesn't return anything but should return a list of comments

    @POST("comments/{commentId}/images")
    suspend fun postGroupEventComment(
        @Path("commentId") id: Int
    ): Response<PostCommentImage>//docs doesn't return anything

}