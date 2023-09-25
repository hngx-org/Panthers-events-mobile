package com.panther.events_app.fragment.events

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.panther.events_app.R
import com.panther.events_app.api.EventsSharedPreference
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
    private val eventsSharedPref by lazy {
        EventsSharedPreference()
    }

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
            val route = EventSubSectionDirections.actionEventSubSectionToEventInfo(it.id ?:"","Group name")
            findNavController().navigate(route)
        }
        binding.addEventFab.setOnClickListener {
            val route = EventSubSectionDirections.actionEventSubSectionToCreateEventDest()
            findNavController().navigate(route)
        }
        binding.backBtn.setOnClickListener{
            findNavController().navigateUp()
        }

        binding.eventHeaderText.setOnClickListener {

            val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
            val googleSignInClient = GoogleSignIn.getClient(requireContext(),googleSignInOptions)

            googleSignInClient.signOut().addOnCompleteListener {
                if (it.isSuccessful) {
                    eventsSharedPref.updateSharedPref("")
                    findNavController().navigate(R.id.sign_in_dest)
                    Toast.makeText(requireContext(), "Sign out successful  ...", Toast.LENGTH_SHORT)
                        .show()
                    return@addOnCompleteListener
                }
                Toast.makeText(requireContext(), "Sign out failed ...", Toast.LENGTH_SHORT)
                    .show()
            }

        }
    }

    private fun loadAllGroupEvents() {
        lifecycleScope.launch {
            eventsViewModel.allGroupEvents.collect { state ->
                binding.retryInfoBtn.isVisible = state is Resource.Failure
                binding.apply {
                    when (state) {

                        is Resource.Loading -> {
                            progressBar.isVisible = true
                            emptyStateTv.isVisible = false
                            eventsRv.isVisible = false
                        }

                        is Resource.Successful -> {
                            progressBar.isVisible = false
                            emptyStateTv.isVisible = false
                            eventsRv.isVisible = true
                            eventsAdapter.submitList(state.data)
                        }

                        is Resource.Failure -> {
                            progressBar.isVisible = false
                            emptyStateTv.isVisible = true
                            retryInfoBtn.setOnClickListener {
                                eventsViewModel.loadAllGroupEvents()
                            }
                            eventsRv.isVisible = false
                            emptyStateTv.text = state.msg
                        }
                    }
                }
            }
        }
    }

}


