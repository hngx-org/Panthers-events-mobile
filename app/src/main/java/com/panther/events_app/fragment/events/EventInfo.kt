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
import androidx.fragment.app.Fragment
import com.panther.events_app.databinding.FragmentEventInfoBinding
import com.panther.events_app.fragment.events.adapters.EventCommentsAdapter
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
}