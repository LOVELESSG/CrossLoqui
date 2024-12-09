package com.example.crossloqui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.compose.CrossLoquiTheme
import com.example.crossloqui.data.NAVIGATION_ITEMS

@Composable
fun CrossLoquiNavigationBar(
    fabIcon: ImageVector,
    fabTarget: String,
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    var currentSelected = currentDestination?.route
    Row(
        modifier = Modifier
            .background(Color.Transparent)
            .padding(16.dp, 0.dp, 16.dp, 16.dp)
    ) {
        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
                .weight(4f)
                .clip(RoundedCornerShape(16.dp))
                .height(56.dp)
        ) {
            NAVIGATION_ITEMS.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = currentSelected == item.route,
                    onClick = {
                        currentSelected = item.route
                        navController.navigate(route = item.route) {
                            //popUpTo(Screen.Home.route)
                            launchSingleTop = true
                        }
                    },
                    icon = { Icon(imageVector = item.icon, contentDescription = item.name) }
                )
            }
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            contentAlignment = Alignment.Center,
        )
        {
            FloatingActionButton(
                onClick = { navController.navigate(route = fabTarget) }
            ) {
                Icon(fabIcon, contentDescription = "Add")
            }
        }
    }
}

@Preview(showSystemUi = false)
@Composable
fun CrossLoquiNavigationBarPreview() {
    CrossLoquiTheme {
        CrossLoquiNavigationBar(Icons.Filled.PersonAdd, "", rememberNavController())
    }
}