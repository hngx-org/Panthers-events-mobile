package com.panther.events_app.arch_com

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panther.events_app.models.LoginResponse
import com.panther.events_app.models.Resource
import com.panther.events_app.models.group_event_model.CommentImageResponse
import com.panther.events_app.models.group_event_model.CommentsResponse
import com.panther.events_app.models.group_event_model.CommentsResponseItem
import com.panther.events_app.models.group_event_model.EventsResponse
import com.panther.events_app.models.group_event_model.EventsResponseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class EventsViewModel : ViewModel() {
    private val eventsRepository = EventsRepository()

    var allGroupEvents = MutableStateFlow<Resource<EventsResponse>>(Resource.Loading())
        private set
    var userSessionInfo = MutableStateFlow<Resource<LoginResponse>>(Resource.Loading())
        private set
    var groupEventInfo = MutableStateFlow<Resource<EventsResponseItem>>(Resource.Loading())
        private set
    var groupEventInfoComments =
        MutableStateFlow<Resource<CommentsResponse>>(Resource.Loading())
        private set

    private val groupEventComments =
        MutableStateFlow<Resource<CommentsResponse>>(Resource.Loading())


    fun loadAllGroupEvents() {
        allGroupEvents.value = Resource.Loading()
        viewModelScope.launch {
            val response = eventsRepository.getAllGroupEvents()

           /* if (response is Resource.Successful) {
                response.data?.let { groupEvent ->
                    val eventList = mutableListOf<EventsResponseItem>()
                    groupEvent.forEach { event ->
                        getCommentsCount(event.id).collect {
                            val newEventItem = event.copy(commentCount = it)
                            eventList.add(newEventItem)
                        }
                    }
                    val newGroupEvent = EventsResponse()
                    newGroupEvent.addAll(eventList)
                    allGroupEvents.value = Resource.Successful(newGroupEvent)
                }
                return@launch
            }*/
            allGroupEvents.value = response

        }
    }

    fun loadGroupEventsInfo(id: String) {
        groupEventInfo.value = Resource.Loading()
        viewModelScope.launch {
            groupEventInfo.value = eventsRepository.getGroupEventInfo(id)

        }
    }

    fun deleteGroupEvents(id: String) {
        viewModelScope.launch {
            eventsRepository.deleteGroupEvent(id)
        }
    }

    private fun loadGroupEventComment() {
        groupEventComments.value = Resource.Loading()
        viewModelScope.launch {
            groupEventComments.value = eventsRepository.getGroupEventComment()
        }
    }

    fun loadGroupEventInfoComments() {
        groupEventInfoComments.value = Resource.Loading()
        viewModelScope.launch {
            groupEventInfoComments.value = eventsRepository.getGroupEventComment()
        }
    }

    fun postGroupEventComments(id: Int) {
        viewModelScope.launch {
            eventsRepository.postGroupEventComment(id)
            loadGroupEventInfoComments()
        }
    }

    private fun getCommentsCount(id: String): Flow<Int> {
        loadGroupEventComment()
        return callbackFlow {
            viewModelScope.launch(Dispatchers.IO) {
                groupEventComments.collect { state ->
                    when (state) {
                        is Resource.Successful -> {
                            val list = mutableListOf<CommentsResponseItem>()
                            state.data?.forEach {comment->
                                if (id == comment.id){
                                    list.add(comment)
                                }
                            }
                            trySend(list.size)
                        }

                        else -> trySend(0)
                    }
                }
            }
            awaitClose()
        }

    }

    fun signIn() {
        userSessionInfo.value = Resource.Loading()
        viewModelScope.launch {
            eventsRepository.signIn().collect{
                userSessionInfo.value = Resource.Successful(LoginResponse().copy(session_token =it ))
            }
//            userSessionInfo.value = eventsRepository.authenticateUser()

        }
    }



}