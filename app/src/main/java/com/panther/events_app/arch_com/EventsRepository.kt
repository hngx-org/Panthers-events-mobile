package com.panther.events_app.arch_com

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.panther.events_app.BASE_URL
import com.panther.events_app.CURRENT_SESSION_TOKEN
import com.panther.events_app.api.EventsSharedPreference
import com.panther.events_app.api.RetrofitInstance
import com.panther.events_app.models.AuthBody
import com.panther.events_app.models.LoginResponse
import com.panther.events_app.models.Resource
import com.panther.events_app.models.events_model.EventResponse
import com.panther.events_app.models.group_event_model.CommentsResponse
import com.panther.events_app.models.group_event_model.CommentsResponseItem
import com.panther.events_app.models.group_event_model.EventsResponse
import com.panther.events_app.models.group_event_model.EventsResponseItem
import com.panther.events_app.models.group_event_model.PostCommentBody
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

class EventsRepository {
    private val apiService = RetrofitInstance().apiService
    private val apiServiceAuth = RetrofitInstance().apiServiceAuth
    private val eventsSharedPref = EventsSharedPreference().getSharedPref()
    fun signIn(authBody: AuthBody): Flow<Resource<LoginResponse>> {

        return callbackFlow {

            val body = Gson().toJson(authBody)
            val jsonObj = JSONObject()
            jsonObj.put("id",authBody.id)
            jsonObj.put("email",authBody.email)
            jsonObj.put("photoUrl",authBody.photoUrl)
            jsonObj.put("name",authBody.name)
            val request = Request.Builder()
                .addHeader("Content-Type", "application/json")
                .url(BASE_URL + "auth/")
                .post(
                    jsonObj.toString().toRequestBody()
                ).build()


            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC
            OkHttpClient.Builder().addInterceptor(logger)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build().newCall(request)
                .enqueue(object :
                    Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        trySend(Resource.Failure(e.message))
                    }

                    override fun onResponse(call: Call, response: Response) {
                         try {
                            val loginInfo = Gson().fromJson(response.body?.string(),LoginResponse::class.java)
                            trySend(Resource.Successful(loginInfo))
                        }catch (e:Exception){
                            trySend(Resource.Failure(e.message))
                        }
                    }
                })
            awaitClose()
        }
    }
    fun postComment(postCommentBody: PostCommentBody): Flow<Resource<CommentsResponseItem>> {

        return callbackFlow {
//            val body = Gson().toJson(postCommentBody)
            val jsonObj = JSONObject()
            jsonObj.put("id",postCommentBody.id)
            jsonObj.put("user",postCommentBody.user)
            jsonObj.put("event",postCommentBody.event)
            jsonObj.put("body",postCommentBody.body)

            val request = okhttp3.Request.Builder()
                .url(BASE_URL + "comments/")
                .addHeader("Content-Type", "application/json")
                .addHeader(
                    "Authorization",
                   "Bearer $eventsSharedPref"
                )
                .post(
                    jsonObj.toString().toRequestBody()
                ).build()


            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC
            OkHttpClient.Builder().addInterceptor(logger)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build().newCall(request)
                .enqueue(object :
                    Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        trySend(Resource.Failure("Unable to post comment ..."))
                    }

                    override fun onResponse(call: Call, response: Response) {
                        try {
                            val commentBody = Gson().fromJson(response.body?.string(),CommentsResponseItem::class.java)
                            trySend(Resource.Successful(commentBody))
                        }catch (e:Exception){
                            trySend(Resource.Failure("Unable to post comment"))
                        }

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

    suspend fun postGroupEventComment(postCommentBody: PostCommentBody): Resource<CommentsResponseItem> {
        return try {
            val body = apiServiceAuth.postGroupEventComment(postCommentBody)
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
            val body = apiServiceAuth.getAllEvents()
            Log.d("JOE", "API RESPONSE (all group event): ${body.body()} ")
            body.body()?.let { Resource.Successful(it) }
                ?: Resource.Failure("Response not available at the moment....")
        } catch (e: Exception) {
            Log.d("JOE", "API RESPONSE :ERROR-> $e ")
            Resource.Failure(e.toString())
        }
    }
}