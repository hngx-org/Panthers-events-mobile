package com.panther.events_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.panther.events_app.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var mainCurrentDestination: NavDestination

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        mainCurrentDestination.id
        outState.putInt(CURRENT_DESTINATION_ID, mainCurrentDestination.id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
       // binding.bottomNavRv.adapter = bottomNavAdapter
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.eventSubSection,
                R.id.eventInfo
            )
        )

        val fragHost = supportFragmentManager.findFragmentById(R.id.frag_host) as NavHostFragment
        navController = fragHost.navController

        navController.currentDestination?.let {
            mainCurrentDestination = it
        }

        toggleBottomNavResources(mainCurrentDestination.id)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            mainCurrentDestination = destination
            toggleBottomNavResources(mainCurrentDestination.id)
            if (!appBarConfiguration.topLevelDestinations.contains(destination.id)){
                binding.bottomNav.visibility = View.GONE
            }else{
                binding.bottomNav.visibility = View.VISIBLE
            }
        }

        savedInstanceState?.let { bundle ->
            val currentDestination =
                bundle.getInt(CURRENT_DESTINATION_ID, mainCurrentDestination.id)
            toggleBottomNavResources(currentDestination)
        }

        binding.apply {
            timelineIcon.setOnClickListener {
                navController.navigate(R.id.eventSubSection)
            }
            peopleIcon.setOnClickListener {
                navController.navigate(R.id.eventInfo)
            }
        }
    }

    private fun toggleBottomNavResources(destination:Int){
        binding.apply {
            when (destination) {
                R.id.eventSubSection -> {
                    timeLineBackground.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.some_yellow))
                    timelineIcon.setImageResource(R.drawable.timeline_filled_icon)
                    peopleBackground.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.white))
                    peopleIcon.setImageResource(R.drawable.people_icon)
                    calendarBackground.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.white))
                    calendarIcon.setImageResource(R.drawable.calendar_icon)
                    settingsBackground.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.white))
                    settingsIcon.setImageResource(R.drawable.settings_icon)
                }
                R.id.eventInfo -> {
                    timeLineBackground.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.white))
                    timelineIcon.setImageResource(R.drawable.timeline_icon)
                    peopleBackground.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.some_yellow))
                    peopleIcon.setImageResource(R.drawable.people_filled_icon)
                    calendarBackground.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.white))
                    calendarIcon.setImageResource(R.drawable.calendar_icon)
                    settingsBackground.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.white))
                    settingsIcon.setImageResource(R.drawable.settings_icon)
                }
                else->Unit

            }
        }
    }
}