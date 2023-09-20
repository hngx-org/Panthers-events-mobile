package com.panther.events_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.panther.events_app.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {
    lateinit var signInBinding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signInBinding = ActivitySignInBinding.inflate(layoutInflater)
        val view = signInBinding.root
        setContentView(view)


    }
}