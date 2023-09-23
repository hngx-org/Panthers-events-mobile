package com.panther.events_app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.panther.events_app.presentation.screens.my_people.AddMyPeopleScreen
import com.panther.events_app.presentation.screens.my_people.MyPeopleScreen

@Composable
fun Navigation(
    navigate: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.MyPeopleScreen.name,
    ){

        composable(route = Screens.MyPeopleScreen.name){
            MyPeopleScreen(onNextClick = navigate, navController = navController)
        }

        composable(route = Screens.AddMyPeopleScreen.name){
            AddMyPeopleScreen()
        }

    }
}