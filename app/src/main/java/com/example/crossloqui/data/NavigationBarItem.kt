package com.example.crossloqui.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.GroupWork
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationBarItem(
    val icon: ImageVector,
    val name: String,
    val route: String
)

val NAVIGATION_ITEMS = arrayListOf(
    NavigationBarItem(
        icon = Icons.Filled.ChatBubble,
        name = "Chat",
        route = "home_screen"
    ),
    NavigationBarItem(
        icon = Icons.Filled.GroupWork,
        name = "Post",
        route = "post_screen"
    ),
    NavigationBarItem(
        icon = Icons.Filled.Contacts,
        name = "Contacts",
        route = "contact_screen"
    ),
    NavigationBarItem(
        icon = Icons.Filled.MoreHoriz,
        name = "More",
        route = "more_screen"
    )
)