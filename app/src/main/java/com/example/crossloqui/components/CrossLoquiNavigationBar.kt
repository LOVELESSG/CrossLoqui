package com.example.crossloqui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarViewDay
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.GroupWork
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.crossloqui.data.NAVIGATION_ITEMS
import com.example.crossloqui.ui.theme.CrossLoquiTheme

@Composable
fun CrossLoquiNavigationBar(
    navController: NavController
) {
    val selectedItem = remember {
        mutableStateOf(NAVIGATION_ITEMS[0])
    }
    Row(
        //verticalAlignment = Alignment.CenterVertically,
        //horizontalArrangement = Arrangement.Center,
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
            NAVIGATION_ITEMS.forEach { item ->
                NavigationBarItem(
                    selected = item == selectedItem.value,
                    onClick = {
                        selectedItem.value = item
                        navController.navigate(route = item.route)
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
                onClick = { /*TODO*/ }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        }
    }
}

@Preview(showSystemUi = false)
@Composable
fun CrossLoquiNavigationBarPreview() {
    CrossLoquiTheme {
        CrossLoquiNavigationBar(rememberNavController())
    }
}