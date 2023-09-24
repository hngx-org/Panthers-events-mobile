package com.panther.events_app.arch_com

import android.util.Log
import com.panther.events_app.api.RetrofitInstance
import com.panther.events_app.api.RetrofitInstance.Companion.apiService
import com.panther.events_app.models.Resource
import com.panther.events_app.models.events_model.EventResponse
import com.panther.events_app.models.group_event_model.CommentImageResponse
import com.panther.events_app.models.group_event_model.GroupEventResponse
import com.panther.events_app.models.group_event_model.GroupEventResponseItem
import com.panther.events_app.models.group_event_model.PostCommentImage

class EventsRepository {
    //private val apiService = RetrofitInstance().apiService
    suspend fun getAllGroupEvents(): Resource<GroupEventResponse> {
        return try {
            val body = apiService.getAllGroupEvents()
            Log.d("JOE", "API RESPONSE (all group event): ${body.body()} ")
            body.body()?.let { Resource.Successful(it) }
                ?: Resource.Failure("Response not available at the moment....")
        } catch (e: Exception) {
            Log.d("JOE", "API RESPONSE :ERROR-> $e ")
            Resource.Failure(e.toString())
        }
    }

    suspend fun getGroupEventInfo(id: Int): Resource<GroupEventResponseItem> {
        return try {
            val body = apiService.getGroupEventInfo(id)
            Log.d("JOE", "API RESPONSE (group event info): ${body.body()} ")
            body.body()?.let { Resource.Successful(it) }
                ?: Resource.Failure("Response not available at the moment....")
        } catch (e: Exception) {
            Log.d("JOE", "API RESPONSE :ERROR-> $e ")
            Resource.Failure(e.toString())
        }
    }

    suspend fun deleteGroupEvent(id: Int): Resource<GroupEventResponseItem> {
        return try {
            val body = apiService.deleteGroupEvent(id)
            Log.d("JOE", "API RESPONSE (delete group event): ${body.body()} ")
            body.body()?.let { Resource.Successful(it) }
                ?: Resource.Failure("Response not available at the moment....")
        } catch (e: Exception) {
            Log.d("JOE", "API RESPONSE :ERROR-> $e ")
            Resource.Failure(e.toString())
        }
    }

    suspend fun getGroupEventComment(id: Int): Resource<CommentImageResponse> {
        return try {
            val body = apiService.getGroupEventComments(id)
            Log.d("JOE", "API RESPONSE (group event comments): ${body.body()} ")
            body.body()?.let { Resource.Successful(it) }
                ?: Resource.Failure("Response not available at the moment....")
        } catch (e: Exception) {
            Log.d("JOE", "API RESPONSE :ERROR-> $e ")
            Resource.Failure(e.toString())
        }
    }

    suspend fun postGroupEventComment(id: Int): Resource<PostCommentImage> {
        return try {
            val body = apiService.postGroupEventComment(id)
            Log.d("JOE", "API RESPONSE (post group event comments): ${body.body()} ")
            body.body()?.let { Resource.Successful(it) }
                ?: Resource.Failure("Response not available at the moment....")
        } catch (e: Exception) {
            Log.d("JOE", "API RESPONSE :ERROR-> $e ")
            Resource.Failure(e.toString())
        }
    }

    suspend fun getAllEvents(): Resource<EventResponse> {
        return try {
            val body = apiService.getAllEvents()
            Log.d("JOE", "API RESPONSE (all group event): ${body.body()} ")
            body.body()?.let { Resource.Successful(it) }
                ?: Resource.Failure("Response not available at the moment....")
        } catch (e: Exception) {
            Log.d("JOE", "API RESPONSE :ERROR-> $e ")
            Resource.Failure(e.toString())
        }
    }
}