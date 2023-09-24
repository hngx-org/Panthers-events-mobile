package com.panther.events_app.api

import com.panther.events_app.models.Events
import com.panther.events_app.models.group_event_model.CommentImageResponse
import com.panther.events_app.models.group_event_model.GroupEventResponse
import com.panther.events_app.models.group_event_model.GroupEventResponseItem
import com.panther.events_app.models.group_event_model.PostCommentImage
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EventsApi {

    suspend fun getEventsResponse() : Response<Events>

    @GET("groupevents")
    suspend fun getAllGroupEvents() : Response<GroupEventResponse>

    @GET("groupevents/{id}")
    suspend fun getGroupEventInfo(
        @Path("id") id:Int
    ): Response<GroupEventResponseItem>

    @DELETE("groupevents/{id}")
    suspend fun deleteGroupEvent(
        @Path("id") id: Int
    ): Response<GroupEventResponseItem>//docs doesn't return anything

    @GET("comments/{commentId}/images/list")
    suspend fun getGroupEventComments(
        @Path("commentId") id: Int
    ): Response<CommentImageResponse>//docs doesn't return anything but should return a list of comments

    @POST("comments/{commentId}/images")
    suspend fun postGroupEventComment(
        @Path("commentId") id: Int
    ): Response<PostCommentImage>//docs doesn't return anything

}