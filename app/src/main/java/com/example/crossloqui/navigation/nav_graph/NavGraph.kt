package com.example.crossloqui.navigation.nav_graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.crossloqui.navigation.Screen
import com.example.crossloqui.ui.contact.ContactScreen
import com.example.crossloqui.ui.homepage.HomepageScreen
import com.example.crossloqui.ui.post.PostScreen

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
            HomepageScreen(navController = navController)
        }
        composable(
            route = Screen.Post.route
        ) {
            //post screen
            PostScreen(navController = navController)
        }
        composable(
            route = Screen.Contact.route
        ) {
            //contact screen
            ContactScreen(navController = navController)
        }
    }
}