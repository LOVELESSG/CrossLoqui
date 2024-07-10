package com.example.crossloqui.ui.contact

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.crossloqui.ui.theme.CrossLoquiTheme

@Composable
fun ContactScreen(
    navController: NavController
) {
}

@Preview(showSystemUi = true)
@Composable
fun ContactScreenPreview() {
    CrossLoquiTheme {
        ContactScreen(navController = rememberNavController())
    }
}