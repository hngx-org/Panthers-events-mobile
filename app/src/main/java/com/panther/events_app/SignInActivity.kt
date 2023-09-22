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
        if (savedInstanceState == null) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(signInBinding.fragmentContainer.id, SignInFragment())
            fragmentTransaction.commit()
        }


    }
}