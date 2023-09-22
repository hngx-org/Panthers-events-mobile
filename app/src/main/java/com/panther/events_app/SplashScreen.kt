package com.panther.events_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.panther.events_app.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    lateinit var spalshScreenBinding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        spalshScreenBinding = ActivitySplashScreenBinding.inflate(layoutInflater)
        val view = spalshScreenBinding.root
        setContentView(view)
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object:Runnable{
            override fun run() {
                val intent = Intent(this@SplashScreen,SignInActivity::class.java)
                startActivity(intent)
            }

        },2000)
    }
}