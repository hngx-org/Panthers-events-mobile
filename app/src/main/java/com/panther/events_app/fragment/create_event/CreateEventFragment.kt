package com.panther.events_app.fragment.create_event

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.panther.events_app.databinding.FragmentCreateEventBinding

class CreateEventFragment : Fragment() {

    private var binding: FragmentCreateEventBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateEventBinding.inflate(inflater, container, false)


        binding!!.btnBack.setOnClickListener {

            val fragmentManager = requireActivity().supportFragmentManager

            fragmentManager.popBackStack()
        }


        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}