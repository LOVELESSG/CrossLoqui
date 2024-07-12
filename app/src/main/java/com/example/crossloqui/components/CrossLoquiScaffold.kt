package com.example.crossloqui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrossLoquiScaffold(title:String, navController: NavController, content: @Composable (PaddingValues) -> Unit){
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        //containerColor = Color.Transparent,
        topBar = { CrossLoquiAppBar(title = { Text(text = title) }) },
        bottomBar = { CrossLoquiNavigationBar(navController) },
        content = content
    )
}