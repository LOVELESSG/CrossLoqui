package com.example.crossloqui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.example.crossloqui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrossLoquiScaffold(title:String, navController: NavController, content: @Composable (PaddingValues) -> Unit){
    val currentDestination = navController.currentDestination?.route
    var titleName = ""
    val fabIcon: ImageVector
    val fabTarget:String
    if (currentDestination.equals(Screen.Home.route)) {
        titleName = title
        fabIcon = Icons.Filled.Add
        fabTarget = ""
    }
    else if (currentDestination.equals(Screen.Post.route)) {
        titleName = title
        fabIcon = Icons.Filled.PostAdd
        fabTarget = ""
    }
    else {
        titleName = title
        fabIcon = Icons.Filled.PersonAdd
        fabTarget = Screen.UserSearchScreen.route
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        //containerColor = Color.Transparent,
        topBar = { CrossLoquiAppBar(title = { Text(text = titleName) }) },
        bottomBar = { CrossLoquiNavigationBar(fabIcon, fabTarget, navController) },
        content = content
    )
}