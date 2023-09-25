package com.panther.events_app.fragment.authentication

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.panther.events_app.R
import com.panther.events_app.api.EventsSharedPreference
import com.panther.events_app.arch_com.EventsViewModel
import com.panther.events_app.databinding.FragmentSignInBinding
import com.panther.events_app.models.AuthBody
import com.panther.events_app.models.LoginResponse
import com.panther.events_app.models.Resource
import kotlinx.coroutines.launch
import java.util.UUID


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

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(requireContext(),googleSignInOptions)

        signInBinding.signInButton.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            signInLauncher.launch(signInIntent)
        }

        signInBinding.signOut.setOnClickListener {
            googleSignInClient.signOut().addOnCompleteListener {task->
                if (task.isSuccessful){
                    Toast.makeText(requireContext(), "Sign out successful  ...", Toast.LENGTH_SHORT)
                        .show()
                    return@addOnCompleteListener
                }
                Toast.makeText(requireContext(), "Sign out failed ...", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    private val signInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            signInBinding.signOut.isVisible = result.resultCode == Activity.RESULT_OK
            when (result.resultCode) {
                Activity.RESULT_OK -> {
                    try {
                        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                        if (task.isSuccessful) {
                            val account = task.result
                            val authBody = AuthBody(
                                email = account.email ?:"no mail",
                                id = account.id ?: "No id ${System.currentTimeMillis()}" ,
                                photoUrl = account.photoUrl.toString(),
                                name = account.givenName +" "+ account.familyName
                            )
                            eventsViewModel.signIn(authBody)
                            loadSignInResponse()

                            return@registerForActivityResult
                        }
                    }catch (e:Exception){
                        Toast.makeText(
                            requireContext(),
                            "Sign In failed ${e.localizedMessage} ...",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }


                }

                else -> {
                    Toast.makeText(requireContext(), "Sign In failed code ...", Toast.LENGTH_SHORT)
                        .show()
                }
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
                            eventsSharedPref.updateSharedPref(userInfo.session_token)
                            eventsSharedPref.updateSharedPrefUser(userInfo.user_id)
                            findNavController().navigate(R.id.timeline_dest)
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

    override fun onDestroy() {
        super.onDestroy()
    }

}