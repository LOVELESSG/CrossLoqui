package com.example.crossloqui.navigation

sealed class Screen(val route: String) {
    object Home: Screen(route = "home_screen")
    object Post: Screen(route = "post_screen")
    object Contact: Screen(route = "contact_screen")
    object Conversation: Screen(route = "conversation_screen")
}