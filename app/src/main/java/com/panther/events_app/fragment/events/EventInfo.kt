package com.panther.events_app.fragment.events

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.panther.events_app.api.EventsViewModel
import com.panther.events_app.databinding.FragmentEventInfoBinding
import com.panther.events_app.fragment.events.adapters.EventCommentsAdapter
import com.panther.events_app.getMyPeopleEventComments
import com.panther.events_app.models.Resource
import kotlinx.coroutines.launch

class EventInfo : Fragment() {

    private lateinit var binding: FragmentEventInfoBinding
    private val commentsAdapter by lazy { EventCommentsAdapter() }
    private val eventsViewModel by activityViewModels<EventsViewModel>()
    private val args by navArgs<EventInfoArgs>()

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
                binding.apply {
                    when (state) {
                        is Resource.Loading -> {
                           progressBar.isVisible = true
                           emptyStateTv.isVisible = false
                        }

                        is Resource.Successful -> {
                           progressBar.isVisible = false
                           emptyStateTv.isVisible = false
                            eventTitleText.text = state.data?.event ?: "Some Event"
                            eventsViewModel.loadGroupEventInfoComments(state.data?.id ?: 0)
                            loadGroupEventInfoComments()

                        }

                        is Resource.Failure -> {
                           progressBar.isVisible = false
                           emptyStateTv.isVisible = true
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
                when (state) {
                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.emptyStateTv.isVisible = false
                        binding.eventCommentsRv.isVisible = false
                    }

                    is Resource.Successful -> {
                        binding.progressBar.isVisible = false
                        binding.emptyStateTv.isVisible = false
                        binding.eventCommentsRv.isVisible = true
                        commentsAdapter.submitList(state.data)
                    }

                    is Resource.Failure -> {
                        binding.progressBar.isVisible = false
                        binding.emptyStateTv.isVisible = true
                        binding.eventCommentsRv.isVisible = false
                        binding.emptyStateTv.text = state.msg
                    }
                }
            }
        }
    }

}