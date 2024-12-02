package com.example.crossloqui.ui.contact

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.crossloqui.navigation.Screen
import com.example.crossloqui.ui.theme.CrossLoquiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserSearchScreen(navController: NavController) {
    var targetEmail by remember {
        mutableStateOf("")
    }
    var active by remember {
        mutableStateOf(false)
    }
    var isFriend by remember {
        mutableStateOf(false)
    }

    SearchBar(
        query = targetEmail,
        onQueryChange = { targetEmail = it },
        onSearch = {
            active = false
            navController.navigate(route = "${Screen.ContactDetail.route}/$isFriend/$targetEmail")
        },
        active = active,
        onActiveChange = { active = it },
        shape = SearchBarDefaults.fullScreenShape,
        placeholder = { Text(text = "Search User ID Here") },
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Search, contentDescription = "Search User")
        },
        trailingIcon = {
            if (active) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Clear",
                    modifier = Modifier.clickable {
                        if (targetEmail.isNotEmpty()) {
                            targetEmail = ""
                        }
                    }
                )
            }
        },
        colors = SearchBarDefaults.colors(containerColor = Color.Transparent),
        modifier = Modifier
            .fillMaxWidth()
    ) {

    }
}

@Preview(showSystemUi = true)
@Composable
fun UserSearchScreenPreview() {
    CrossLoquiTheme {
        UserSearchScreen(navController = rememberNavController())
    }
}