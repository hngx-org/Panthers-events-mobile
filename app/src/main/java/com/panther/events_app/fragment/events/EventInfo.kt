package com.panther.events_app.fragment.events

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.panther.events_app.CURRENT_SESSION_TOKEN
import com.panther.events_app.R
import com.panther.events_app.api.EventsSharedPreference
import com.panther.events_app.arch_com.EventsViewModel
import com.panther.events_app.databinding.FragmentEventInfoBinding
import com.panther.events_app.fragment.events.adapters.EventCommentsAdapter
import com.panther.events_app.getDate
import com.panther.events_app.getDuration
import com.panther.events_app.isValid
import com.panther.events_app.models.Resource
import com.panther.events_app.models.group_event_model.PostCommentBody
import kotlinx.coroutines.launch
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
class EventInfo : Fragment() {

    private lateinit var binding: FragmentEventInfoBinding
    private val commentsAdapter by lazy { EventCommentsAdapter() }
    private val eventsViewModel by activityViewModels<EventsViewModel>()
    private val args by navArgs<EventInfoArgs>()
    private val eventsSharedPref = EventsSharedPreference().getSharedPrefUser()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEventInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.eventCommentsRv.adapter = commentsAdapter
//        commentsAdapter.submitList(getMyPeopleEventComments())
        eventsViewModel.loadGroupEventsInfo(args.eventId)
        loadGroupEventInfo()
        binding.addImageBtn.setOnClickListener {
            selectImage()
        }
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    private val requestAccountImgPicker =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data?.data
                data?.let { uri ->
                   //do something
                }
                return@registerForActivityResult
            }
            Toast.makeText(requireContext(), "Unable to select image", Toast.LENGTH_SHORT).show()
        }

    private fun selectImage() {
        val intent = Intent(
            Intent.ACTION_GET_CONTENT,
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        ).apply {
            type = "image/*"
        }
        requestAccountImgPicker.launch(intent)

    }


    private fun loadGroupEventInfo() {
        lifecycleScope.launch {
            eventsViewModel.groupEventInfo.collect { state ->
                binding.retryInfoBtn.isVisible = state is Resource.Failure
                binding.apply {
                    when (state) {
                        is Resource.Loading -> {
                           progressBar.isVisible = true
                           emptyStateTv.isVisible = false
                        }

                        is Resource.Successful -> {
                           progressBar.isVisible = false
                           emptyStateTv.isVisible = false
                            eventHeaderText.text = "Group name"
                            eventTitleText.text = state.data?.title?.isValid()
                            eventLocationText.text = state.data?.location?.isValid()
                            eventDurationText.text = getDuration( state.data?.startDate,state.data?.endDate)
                            eventDateText.text = getDate( state.data?.startDate)
                            eventsViewModel.loadGroupEventInfoComments()
                            loadGroupEventInfoComments()

                            sendBtn.setOnClickListener {
                                val comment = commentEditText.text.toString().trim()
                                if (comment.isNotEmpty()) {
                                    val commentBody = PostCommentBody(
                                        body = commentEditText.text.toString().trim(),
                                        event = state.data?.id ?:"No id",
                                        id = UUID.randomUUID().toString(),
                                        user = eventsSharedPref,
                                    )

                                    eventsViewModel.postGroupEventComments(commentBody)
                                    observeSentCommentState()
                                    return@setOnClickListener
                                }
                                Toast.makeText(
                                    requireContext(),
                                    "Field cannot be empty",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }

                        is Resource.Failure -> {
                           progressBar.isVisible = false
                           emptyStateTv.isVisible = true
                           retryInfoBtn.setOnClickListener {
                               eventsViewModel.loadGroupEventsInfo(args.eventId)
                           }
                           emptyStateTv.text = state.msg
                        }
                    }
                }
            }
        }
    }

    private fun loadGroupEventInfoComments() {
        lifecycleScope.launch {
            eventsViewModel.groupEventInfoComments.collect { state ->
                binding.retryCommentBtn.isVisible = state is Resource.Failure
                binding.apply {
                    when (state) {
                        is Resource.Loading -> {
                            progressBar.isVisible = true
                            emptyStateTv.isVisible = false
                            eventCommentsRv.isVisible = false
                        }

                        is Resource.Successful -> {
                            progressBar.isVisible = false
                            emptyStateTv.isVisible = false
                            eventCommentsRv.isVisible = true
                            val list = state.data?.filter { it.event == args.eventId } ?: emptyList()
                            if (list.isNotEmpty()) {
                                commentsAdapter.submitList(list)
                                binding.eventCommentsRv.layoutManager?.smoothScrollToPosition(binding.eventCommentsRv,null,0)
                                return@collect
                            }
                            emptyStateTv.isVisible = true
                            emptyStateTv.text = "No comments found"


                        }

                        is Resource.Failure -> {
                            progressBar.isVisible = false
                            emptyStateTv.isVisible = true
                            retryCommentBtn.setOnClickListener {
                                eventsViewModel.loadGroupEventInfoComments()
                                loadGroupEventInfoComments()
                            }
                            eventCommentsRv.isVisible = false
                            emptyStateTv.text = state.msg
                        }
                    }
                }
            }
        }
    }

    private fun observeSentCommentState() {
        lifecycleScope.launch {
            eventsViewModel.sendCommentStatus.collect { state ->
                binding.apply {
                    when (state) {
                        is Resource.Loading -> {
                            commentProgressBar.visibility = View.VISIBLE
                            sendBtn.visibility = View.INVISIBLE
                        }

                        is Resource.Successful -> {
                            commentProgressBar.visibility = View.INVISIBLE
                            sendBtn.visibility = View.VISIBLE
                            commentEditText.text.clear()
                        }

                        is Resource.Failure -> {
                            commentProgressBar.visibility = View.INVISIBLE
                            sendBtn.visibility = View.VISIBLE
                            Toast.makeText(requireContext(), state.msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

}