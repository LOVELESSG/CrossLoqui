package com.example.crossloqui.navigation

sealed class Screen(val route: String) {
    object Home: Screen(route = "home_screen")
    object Post: Screen(route = "post_screen")
    object Contact: Screen(route = "contact_screen")
    object Conversation: Screen(route = "conversation_screen")
    object Login: Screen(route = "login_screen")
    object Register: Screen(route = "register_screen")
    object AccountInfo: Screen(route = "account_info_screen")
}