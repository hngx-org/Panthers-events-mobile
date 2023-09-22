package com.panther.events_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.panther.events_app.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    lateinit var splashScreenBinding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreenBinding = ActivitySplashScreenBinding.inflate(layoutInflater)
        val view = splashScreenBinding.root
        setContentView(view)
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object:Runnable{
            override fun run() {
                val intent = Intent(this@SplashScreen, MainActivity::class.java)
                startActivity(intent)
            }

        },2000)
    }
}