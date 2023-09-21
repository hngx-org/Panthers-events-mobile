package com.panther.events_app.fragment.timeline

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.panther.events_app.R
import com.panther.events_app.adapter.TimelineAdapter
import com.panther.events_app.databinding.FragmentTimelineEveryoneBinding
import com.panther.events_app.models.Items


class TimelineEveryoneFragment : Fragment() {

    private var _binding: FragmentTimelineEveryoneBinding? = null
    private val binding get() = _binding!!
    private lateinit var timelineAdapter: TimelineAdapter

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

        val itemList = dummyData()
        val colorList = getColorList()
        timelineAdapter = TimelineAdapter(itemList, colorList)

        binding.everyoneTimelineRv.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = timelineAdapter
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
    private fun dummyData(): List<Items> {
        val list = ArrayList<Items>()

        for (i in 1..10) {
            list.add(
                Items("Football game $i", "May 20, 2023 $i", "Friday 4 - 6pm $i",
                    "Teslim Balogun Stadium $i", "2 weeks $i")
            )
        }
        return list
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}