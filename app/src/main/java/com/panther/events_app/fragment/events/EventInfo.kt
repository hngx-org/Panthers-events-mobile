package com.panther.events_app.fragment.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.panther.events_app.databinding.FragmentEventInfoBinding
import com.panther.events_app.getMyPeopleEventComments

class EventInfo : Fragment() {

    private lateinit var binding: FragmentEventInfoBinding
    private val commentsAdapter by lazy { EventCommentsAdapter() }

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
        commentsAdapter.submitList(getMyPeopleEventComments())
        binding.eventCommentsRv.setOnClickListener{
            //findNavController().navigate(R.id.eventSubSection)
        }
    }
}