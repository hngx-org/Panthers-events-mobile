package com.panther.events_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.panther.events_app.databinding.FragmentSignInBinding


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

       return signInBinding.root
    }

}