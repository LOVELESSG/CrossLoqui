package com.example.crossloqui.navigation

sealed class Screen(val route: String) {
    object Home: Screen(route = "home_screen")
    object Post: Screen(route = "post_screen")
    object Contact: Screen(route = "contact_screen")
    object Conversation: Screen(route = "conversation_screen")
    object Login: Screen(route = "login_screen")
    object Register: Screen(route = "register_screen")
    object AccountInfo: Screen(route = "account_info_screen")
    object UserDetail: Screen(route = "user_detail_screen")
    object ContactDetail: Screen(route = "contact_detail_screen")
    object UserSearchScreen: Screen(route = "user_search_screen")
    object FriendRequestScreen: Screen(route = "friend_request_screen")
}