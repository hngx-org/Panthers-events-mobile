package com.panther.events_app

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.panther.events_app.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
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

        setSupportActionBar(binding.toolbar)

        val fragmentContainerHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = fragmentContainerHost.findNavController()

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.timeline_dest,
                R.id.my_people_dest,
                R.id.calendar_dest,
                R.id.settinggs_dest
                )
        )

        setupActionBar(navController, appBarConfiguration)

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

            if (destination.id== R.id.eventInfo || destination.id == R.id.eventSubSection){
                binding.appBarLayout.visibility = View.GONE
            }else{
                binding.appBarLayout.visibility = View.VISIBLE
            }

        }

        savedInstanceState?.let { bundle ->
            val currentDestination =
                bundle.getInt(CURRENT_DESTINATION_ID, mainCurrentDestination.id)
            toggleBottomNavResources(currentDestination)
        }

        binding.apply {
            timelineIcon.setOnClickListener {
                navController.navigate(R.id.timeline_dest)
            }
            peopleIcon.setOnClickListener {
                navController.navigate(R.id.my_people_dest)
            }
            calendarIcon.setOnClickListener {
                navController.navigate(R.id.calendar_dest)
            }
            settingsIcon.setOnClickListener {
                navController.navigate(R.id.settinggs_dest)
            }
        }

    }

    private fun setupActionBar(navController: NavController,
                               appBarConfig : AppBarConfiguration) {
        setupActionBarWithNavController(navController, appBarConfig)
    }

    private fun toggleBottomNavResources(destination:Int){
        binding.apply {
            when (destination) {
                R.id.timeline_dest -> {
                    timeLineBackground.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.PrimaryColor))
                    timelineIcon.setImageResource(R.drawable.timeline_filled_icon)
                    peopleBackground.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.white))
                    peopleIcon.setImageResource(R.drawable.people_icon)
                    calendarBackground.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.white))
                    calendarIcon.setImageResource(R.drawable.calendar_icon)
                    settingsBackground.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.white))
                    settingsIcon.setImageResource(R.drawable.settings_icon)
                }
                R.id.my_people_dest -> {
                    timeLineBackground.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.white))
                    timelineIcon.setImageResource(R.drawable.timeline_icon)
                    peopleBackground.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.orange))
                    peopleIcon.setImageResource(R.drawable.people_filled_icon)
                    calendarBackground.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.white))
                    calendarIcon.setImageResource(R.drawable.calendar_icon)
                    settingsBackground.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.white))
                    settingsIcon.setImageResource(R.drawable.settings_icon)
                }
                R.id.calendar_dest -> {
                    timeLineBackground.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.white))
                    timelineIcon.setImageResource(R.drawable.timeline_icon)
                    peopleBackground.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.white))
                    peopleIcon.setImageResource(R.drawable.people_icon)
                    calendarBackground.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.SecondaryColor))
                    calendarIcon.setImageResource(R.drawable.calendar_filled_icon)
                    settingsBackground.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.white))
                    settingsIcon.setImageResource(R.drawable.settings_icon)
                }
                R.id.settinggs_dest -> {
                    timeLineBackground.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.white))
                    timelineIcon.setImageResource(R.drawable.timeline_icon)
                    peopleBackground.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.white))
                    peopleIcon.setImageResource(R.drawable.people_icon)
                    calendarBackground.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.white))
                    calendarIcon.setImageResource(R.drawable.calendar_filled_icon)
                    settingsBackground.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.blue))
                    settingsIcon.setImageResource(R.drawable.settings_icon)
                }
                else->Unit

            }
        }
    }
}