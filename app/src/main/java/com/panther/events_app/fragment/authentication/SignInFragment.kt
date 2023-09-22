package com.panther.events_app.fragment.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.panther.events_app.R
import com.panther.events_app.databinding.FragmentSignInBinding
import com.panther.events_app.fragment.timeline.TimelineFragment
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen


class SignInFragment : Fragment() {

lateinit var signInBinding:FragmentSignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val signInBinding = FragmentSignInBinding.inflate(layoutInflater)
        signInBinding.signInButton.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_timeline_dest)
        }

       return signInBinding.root
    }

}