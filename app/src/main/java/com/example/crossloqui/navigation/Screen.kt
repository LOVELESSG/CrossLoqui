package com.example.crossloqui.navigation

sealed class Screen(val route: String) {
    data object Home: Screen(route = "home_screen")
    data object Post: Screen(route = "post_screen")
    data object Contact: Screen(route = "contact_screen")
    data object Conversation: Screen(route = "conversation_screen")
    data object Login: Screen(route = "login_screen")
    data object Register: Screen(route = "register_screen")
    data object AccountInfo: Screen(route = "account_info_screen")
    data object ContactDetail: Screen(route = "contact_detail_screen")
    data object UserSearchScreen: Screen(route = "user_search_screen")
    data object FriendRequestScreen: Screen(route = "friend_request_screen")
}