package com.panther.events_app.arch_com

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panther.events_app.models.Resource
import com.panther.events_app.models.group_event_model.CommentImageResponse
import com.panther.events_app.models.group_event_model.GroupEventResponse
import com.panther.events_app.models.group_event_model.GroupEventResponseItem
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class EventsViewModel : ViewModel() {
    private val eventsRepository = EventsRepository()

    var allGroupEvents = MutableStateFlow<Resource<GroupEventResponse>>(Resource.Loading())
        private set
    var groupEventInfo = MutableStateFlow<Resource<GroupEventResponseItem>>(Resource.Loading())
        private set
    var groupEventInfoComments =
        MutableStateFlow<Resource<CommentImageResponse>>(Resource.Loading())
        private set

    private val groupEventComments =
        MutableStateFlow<Resource<CommentImageResponse>>(Resource.Loading())


    fun loadAllGroupEvents() {
        allGroupEvents.value = Resource.Loading()
        viewModelScope.launch {
            val response = eventsRepository.getAllGroupEvents()

            if (response is Resource.Successful) {
                response.data?.let { groupEvent ->
                    val eventList = mutableListOf<GroupEventResponseItem>()
                    groupEvent.forEach { event ->
                        getCommentsCount(event.id).collect {
                            val newEventItem = event.copy(commentCount = it)
                            eventList.add(newEventItem)
                        }
                    }
                    val newGroupEvent = GroupEventResponse()
                    newGroupEvent.addAll(eventList)
                    allGroupEvents.value = Resource.Successful(newGroupEvent)
                }
                return@launch
            }
            allGroupEvents.value = response

        }
    }

    fun loadGroupEventsInfo(id: Int) {
        groupEventInfo.value = Resource.Loading()
        viewModelScope.launch {
            groupEventInfo.value = eventsRepository.getGroupEventInfo(id)

        }
    }

    fun deleteGroupEvents(id: Int) {
        viewModelScope.launch {
            eventsRepository.deleteGroupEvent(id)
        }
    }

    private fun loadGroupEventComment(id: Int) {
        groupEventComments.value = Resource.Loading()
        viewModelScope.launch {
            groupEventComments.value = eventsRepository.getGroupEventComment(id)
        }
    }

    fun loadGroupEventInfoComments(id: Int) {
        groupEventInfoComments.value = Resource.Loading()
        viewModelScope.launch {
            groupEventInfoComments.value = eventsRepository.getGroupEventComment(id)
        }
    }

    fun postGroupEventComments(id: Int) {
        viewModelScope.launch {
            eventsRepository.postGroupEventComment(id)
            loadGroupEventInfoComments(id)
        }
    }

    private fun getCommentsCount(id: Int): Flow<Int> {
        loadGroupEventComment(id)
        return callbackFlow {
            viewModelScope.launch {
                groupEventComments.collect { state ->
                    when (state) {
                        is Resource.Successful -> {
                            trySend(state.data?.size ?: 0)
                        }

                        else -> trySend(0)
                    }
                }
            }
            awaitClose()
        }

    }


}