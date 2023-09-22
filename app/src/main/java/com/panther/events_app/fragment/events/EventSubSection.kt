package com.panther.events_app.fragment.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.panther.events_app.databinding.FragmentEventSubSectionBinding
import com.panther.events_app.fragment.events.adapters.MyPeopleEventAdapter
import com.panther.events_app.getMyPeopleEventList


class EventSubSection : Fragment() {
    private lateinit var binding: FragmentEventSubSectionBinding
    private val eventsAdapter by lazy { MyPeopleEventAdapter() }

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
        eventsAdapter.submitList(getMyPeopleEventList())
        eventsAdapter.adapterClickListener {
            val route = EventSubSectionDirections.actionEventSubSectionToEventInfo()
            findNavController().navigate(route)
        }
        binding.backBtn.setOnClickListener{
            findNavController().navigateUp()
        }
    }

}