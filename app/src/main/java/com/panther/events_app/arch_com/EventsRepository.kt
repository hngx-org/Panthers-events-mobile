package com.panther.events_app.arch_com

import android.util.Log
import com.panther.events_app.BASE_URL
import com.panther.events_app.api.RetrofitInstance
import com.panther.events_app.models.LoginResponse
import com.panther.events_app.models.Resource
import com.panther.events_app.models.group_event_model.CommentImageResponse
import com.panther.events_app.models.group_event_model.CommentsResponse
import com.panther.events_app.models.group_event_model.EventsResponse
import com.panther.events_app.models.group_event_model.EventsResponseItem
import com.panther.events_app.models.group_event_model.GroupEventResponseItem
import com.panther.events_app.models.group_event_model.PostCommentImage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit

class EventsRepository {
    private val apiService = RetrofitInstance().apiService
    private val apiServiceAuth = RetrofitInstance().apiServiceAuth

    fun signIn(): Flow<String> {

        return callbackFlow {

            val request = Request.Builder()
                .addHeader("Content-Type", "application/json")
                .url(BASE_URL + "login")
                .build()


            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC
            OkHttpClient.Builder().addInterceptor(logger)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build().newCall(request)
                .enqueue(object :
                    Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        Log.d("Auth", "onFailure-- $e")
                        trySend("")
                    }

                    override fun onResponse(call: Call, response: Response) {
                        Log.d(
                            "Auth", "onResponse-- $response +++ " +
                                    "${call.isExecuted()}====${response.isSuccessful}" +
                                    "--${response.code}===${response.body}"
                        )
                        Log.d("Auth", "RESPONSE BODY::${response.body?.string()}")
                        trySend(response.request.url.toString())
//                        trySend(response.priorResponse?.request?.url.toString()) this leads to an error in the server or something
                    }
                })
            awaitClose()
        }
    }

    suspend fun authenticateUser(): Resource<LoginResponse> {

        return try {
            val body = apiService.authenticateUser()
            Log.d("JOE", "API RESPONSE (authenticate user ): ${body.body()} ")
            body.body()?.let {
                if (it.success) {
                    Resource.Successful(it)
                } else {
                    Resource.Failure("Unable to sign in")
                }
            } ?: Resource.Failure("Unable to sign in at the moment....")
        } catch (e: Exception) {
            Log.d("JOE", "API RESPONSE :ERROR-> $e ")
            Resource.Failure(e.toString())
        }
    }
    suspend fun getAllGroupEvents(): Resource<EventsResponse> {
        return try {
            val body = apiServiceAuth.getAllGroupEvents()
            Log.d("JOE", "API RESPONSE (all group event): ${body.body()} ")
            body.body()?.let { Resource.Successful(it) }
                ?: Resource.Failure("Response not available at the moment....")
        } catch (e: Exception) {
            Log.d("JOE", "API RESPONSE :ERROR-> $e ")
            Resource.Failure(e.toString())
        }
    }

    suspend fun getGroupEventInfo(id: String): Resource<EventsResponseItem> {
        return try {
            val body = apiServiceAuth.getGroupEventInfo(id)
            Log.d("JOE", "API RESPONSE (group event info): ${body.body()} ")
            body.body()?.let { Resource.Successful(it) }
                ?: Resource.Failure("Response not available at the moment....")
        } catch (e: Exception) {
            Log.d("JOE", "API RESPONSE :ERROR-> $e ")
            Resource.Failure(e.toString())
        }
    }

    suspend fun deleteGroupEvent(id: String): Resource<GroupEventResponseItem> {
        return try {
            val body = apiServiceAuth.deleteGroupEvent(id)
            Log.d("JOE", "API RESPONSE (delete group event): ${body.body()} ")
            body.body()?.let { Resource.Successful(it) }
                ?: Resource.Failure("Response not available at the moment....")
        } catch (e: Exception) {
            Log.d("JOE", "API RESPONSE :ERROR-> $e ")
            Resource.Failure(e.toString())
        }
    }

    suspend fun getGroupEventComment(): Resource<CommentsResponse> {
        return try {
            val body = apiServiceAuth.getGroupEventComments()
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
            val body = apiServiceAuth.postGroupEventComment(id)
            Log.d("JOE", "API RESPONSE (post group event comments): ${body.body()} ")
            body.body()?.let { Resource.Successful(it) }
                ?: Resource.Failure("Response not available at the moment....")
        } catch (e: Exception) {
            Log.d("JOE", "API RESPONSE :ERROR-> $e ")
            Resource.Failure(e.toString())
        }
    }
}