package com.example.crossloqui.ui.contact

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.captionBarIgnoringVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsIgnoringVisibility
import androidx.compose.foundation.layout.systemBarsIgnoringVisibility
import androidx.compose.foundation.layout.tappableElementIgnoringVisibility
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.crossloqui.ui.theme.CrossLoquiTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun UserSearchScreen(navController: NavController) {
    var userId by remember {
        mutableStateOf("")
    }
    var active by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    SearchBar(
        query = userId,
        onQueryChange = { userId = it },
        onSearch = {
            active = false
            Toast.makeText(context, "searching $userId", Toast.LENGTH_LONG).show()
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
                        if (userId.isNotEmpty()) {
                            userId = ""
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