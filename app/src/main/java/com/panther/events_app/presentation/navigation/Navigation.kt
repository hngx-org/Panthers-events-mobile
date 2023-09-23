package com.panther.events_app.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.panther.events_app.presentation.screens.my_people.AddMyPeopleScreen
import com.panther.events_app.presentation.screens.my_people.MyPeopleScreen

@Composable
fun Navigation(
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.MyPeopleScreen.name,
    ){

        composable(route = Screens.MyPeopleScreen.name){
          //  MyPeopleScreen()
        }

        composable(route = Screens.AddMyPeopleScreen.name){
            AddMyPeopleScreen()
        }

    }
}