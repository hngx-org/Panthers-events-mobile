package com.panther.events_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.panther.events_app.databinding.FragmentEventSubSectionBinding


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
    }

}