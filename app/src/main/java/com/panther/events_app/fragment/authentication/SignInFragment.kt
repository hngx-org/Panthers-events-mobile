package com.panther.events_app.fragment.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.panther.events_app.R
import com.panther.events_app.databinding.FragmentSignInBinding


class SignInFragment : Fragment() {

    private var _signInBinding : FragmentSignInBinding? = null
    private val signInBinding get() = _signInBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _signInBinding = FragmentSignInBinding.inflate(layoutInflater)

        return signInBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signInBinding.signInButton.setOnClickListener {
            findNavController().navigate(R.id.action_sign_in_dest_to_timeline_dest)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}