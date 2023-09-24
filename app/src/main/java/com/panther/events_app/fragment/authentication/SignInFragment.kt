package com.panther.events_app.fragment.authentication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.panther.events_app.api.EventsSharedPreference
import com.panther.events_app.arch_com.EventsViewModel
import com.panther.events_app.databinding.FragmentSignInBinding
import com.panther.events_app.models.Resource
import kotlinx.coroutines.launch


class SignInFragment : Fragment() {

    private var _signInBinding : FragmentSignInBinding? = null
    private val signInBinding get() = _signInBinding!!
    private val eventsViewModel by activityViewModels<EventsViewModel>()

    private val eventsSharedPref by lazy {
        EventsSharedPreference()
    }

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
            eventsViewModel.signIn()
            loadSignInResponse()
        }

    }

    private fun loadSignInResponse() {
        lifecycleScope.launch {
            eventsViewModel.userSessionInfo.collect { state ->
                when (state) {
                    is Resource.Loading -> {
                        signInBinding.progressBar.isVisible = true
                    }

                    is Resource.Successful -> {
                        signInBinding.progressBar.isVisible = false
                        state.data?.let {userInfo->
                            Log.d("AUth", "loadSignInResponse: ${userInfo.session_token}")

                            Intent(Intent.ACTION_VIEW).apply {
//                                data = Uri.parse(userInfo.session_token)
                                data = Uri.parse("https://octopus-app-nax2o.ondigitalocean.app/api/login")
                                startActivity(this)
                            }
                            eventsSharedPref.updateSharedPref(userInfo.session_token)
//                            findNavController().navigate(R.id.action_sign_in_dest_to_timeline_dest)
                        }
                    }

                    is Resource.Failure -> {
                        signInBinding.progressBar.isVisible = false
                        Toast.makeText(requireContext(), "${state.msg}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    private fun getShareIntent(url: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, url)
        startActivity(Intent.createChooser(intent, "Share"))
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}