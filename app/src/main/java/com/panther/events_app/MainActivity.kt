package com.panther.events_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.findNavController
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.panther.events_app.bottom_nav.BottomNavAdapter
import com.panther.events_app.bottom_nav.BottomNavItem
import com.panther.events_app.bottom_nav.BottomNavItem.Companion.getIcon
import com.panther.events_app.databinding.ActivityMainBinding

const val CURRENT_DESTINATION_ID = "current destination ID"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private val bottomNavAdapter by lazy { BottomNavAdapter() }
    private lateinit var mainCurrentDestination: NavDestination
    private lateinit var fragmentList: List<BottomNavItem>
    private lateinit var editableFragmentList: MutableList<BottomNavItem>

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        mainCurrentDestination.id
        outState.putInt(CURRENT_DESTINATION_ID, mainCurrentDestination.id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavRv.adapter = bottomNavAdapter
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

        fragmentList = appBarConfiguration.topLevelDestinations.map {
            BottomNavItem(
                title = it,
                icon = getIcon(it),
                isSelected = false
            )
        }.reversed()


        editableFragmentList = mutableListOf()
        editableFragmentList.add(
            BottomNavItem(
                title = mainCurrentDestination.id,
                icon = mainCurrentDestination.id,
                isSelected = true
            )
        )
        toggleBottomNav(mainCurrentDestination.id)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            mainCurrentDestination = destination
            toggleBottomNav(mainCurrentDestination.id)

        }

        savedInstanceState?.let { bundle ->
            val currentDestination =
                bundle.getInt(CURRENT_DESTINATION_ID, mainCurrentDestination.id)
            toggleBottomNav(currentDestination)
        }

        bottomNavAdapter.adapterClickListener { fragId ->
            navController.navigate(fragId)
        }

    }

    private fun toggleBottomNav(destination: Int) {
        val currentFragment =
            editableFragmentList.find { it.title == destination } ?: BottomNavItem()
        val newList = mutableListOf<BottomNavItem>()
        fragmentList.forEach {
            if (it.title == currentFragment.title) {
                newList.add(it.copy(isSelected = true))
            } else {
                newList.add(it)
            }
        }
        editableFragmentList = newList
        bottomNavAdapter.submitList(editableFragmentList)
        Log.d("JPETAG", "onCreate: $editableFragmentList")
    }
}