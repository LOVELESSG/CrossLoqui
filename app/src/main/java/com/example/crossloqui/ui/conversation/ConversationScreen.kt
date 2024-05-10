package com.example.crossloqui.ui.conversation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.crossloqui.components.CrossLoquiAppBar
import com.example.crossloqui.components.CrossLoquiScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationScreen(
    navController: NavController
) {
    Scaffold(
        topBar = { CrossLoquiAppBar(title = { Text(text = "Conversation") }) }
    ) {paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Box(modifier = Modifier.weight(1f)){
                Text(text = "Hello")
            }
            ConversationInput()
        }
    }
}

@Preview
@Composable
fun ConversationScreenPreview() {
    ConversationScreen(navController = NavController(androidx.compose.ui.platform.LocalContext.current))
}