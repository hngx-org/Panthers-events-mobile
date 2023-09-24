package com.panther.events_app.fragment.timeline

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.panther.events_app.R
import com.panther.events_app.adapter.TimelineAdapter
import com.panther.events_app.arch_com.EventsViewModel
import com.panther.events_app.databinding.FragmentTimelineEveryoneBinding
import com.panther.events_app.models.Items
import com.panther.events_app.models.Resource
import kotlinx.coroutines.launch


class TimelineEveryoneFragment : Fragment() {

    private var _binding: FragmentTimelineEveryoneBinding? = null
    private val binding get() = _binding!!
    private lateinit var timelineAdapter: TimelineAdapter
    private val eventsViewModel by activityViewModels<EventsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTimelineEveryoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {

        val colorList = getColorList()
        timelineAdapter = TimelineAdapter(colorList)

        binding.everyoneTimelineRv.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = timelineAdapter
            eventsViewModel.getAllEvents
            loadAllEvents()
        }
    }
    private fun getColorList(): List<Int> {
        return listOf(
            ContextCompat.getColor(requireActivity(), R.color.PrimaryColor),
            ContextCompat.getColor(requireActivity(), R.color.SecondaryColor),
            ContextCompat.getColor(requireActivity(), R.color.red),
            ContextCompat.getColor(requireActivity(), R.color.orange),
            ContextCompat.getColor(requireActivity(), R.color.blue)
        )
    }

    private fun loadAllEvents() {
        lifecycleScope.launch {
            eventsViewModel.getAllEvents.collect { state ->
                binding.apply {
                    when (state) {
                        is Resource.Loading -> {
                            progressBar.isVisible = true
                            emptyStateTv.isVisible = false
                        }

                        is Resource.Successful -> {
                            progressBar.isVisible = false
                            emptyStateTv.isVisible = false

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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}