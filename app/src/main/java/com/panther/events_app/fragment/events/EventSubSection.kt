package com.panther.events_app.fragment.events

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.panther.events_app.arch_com.EventsViewModel
import com.panther.events_app.databinding.FragmentEventSubSectionBinding
import com.panther.events_app.fragment.events.adapters.MyPeopleEventAdapter
import com.panther.events_app.getMyPeopleEventList
import com.panther.events_app.models.Resource
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
class EventSubSection : Fragment() {
    private lateinit var binding: FragmentEventSubSectionBinding
    private val eventsAdapter by lazy { MyPeopleEventAdapter() }
    private val eventsViewModel by activityViewModels<EventsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEventSubSectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.eventsRv.adapter = eventsAdapter
//        eventsAdapter.submitList(getMyPeopleEventList())
        eventsViewModel.loadAllGroupEvents()
        loadAllGroupEvents()
        eventsAdapter.adapterClickListener {
            val route = EventSubSectionDirections.actionEventSubSectionToEventInfo(it.id,"Group name")
            findNavController().navigate(route)
        }
        binding.addEventFab.setOnClickListener {
            val route = EventSubSectionDirections.actionEventSubSectionToCreateEventDest()
            findNavController().navigate(route)
        }
        binding.backBtn.setOnClickListener{
            findNavController().navigateUp()
        }
    }

    private fun loadAllGroupEvents() {
        lifecycleScope.launch {
            eventsViewModel.allGroupEvents.collect { state ->
                when (state) {
                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.emptyStateTv.isVisible = false
                        binding.eventsRv.isVisible = false
                    }

                    is Resource.Successful -> {
                        binding.progressBar.isVisible = false
                        binding.emptyStateTv.isVisible = false
                        binding.eventsRv.isVisible = true
                        eventsAdapter.submitList(state.data)
                    }

                    is Resource.Failure -> {
                        binding.progressBar.isVisible = false
                        binding.emptyStateTv.isVisible = true
                        binding.eventsRv.isVisible = false
                        binding.emptyStateTv.text = state.msg
                    }
                }
            }
        }
    }

}


