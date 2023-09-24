package com.panther.events_app.presentation.screens.my_people

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.panther.events_app.R
import com.panther.events_app.presentation.navigation.Navigation

class MyPeopleFragment : Fragment() {


    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navigate = {
            findNavController().navigate(R.id.action_my_people_dest_to_eventSubSection)
        }

        composeView.setContent {

//            MyPeopleScreen(
//                onNextClick = { navigate.invoke() } ,
////                navController = navController
//            )

            Navigation(navigate =  navigate)

        }
    }
}