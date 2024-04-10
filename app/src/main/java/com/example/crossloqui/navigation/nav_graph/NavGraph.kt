package com.example.crossloqui.navigation.nav_graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.crossloqui.navigation.Screen

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.Home.route
        ) {
            //home screen
        }
        composable(
            route = Screen.Post.route
        ) {
            //post screen
        }
        composable(
            route = Screen.Contact.route
        ) {
            //contact screen
        }
    }
}