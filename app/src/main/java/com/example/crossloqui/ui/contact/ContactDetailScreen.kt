package com.example.crossloqui.ui.contact

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Textsms
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.crossloqui.R
import com.example.crossloqui.components.CrossLoquiAppBar
import com.example.crossloqui.ui.theme.CrossLoquiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetailScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete Contacts"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        //Should judge whether the user has been followed by current user
        //val haveFollowed: Boolean
        ContactDetailContent(paddingValues, navController = navController, true)
    }
}


@Composable
fun ContactDetailContent(
    paddingValues: PaddingValues,
    navController: NavController,
    haveFollowed: Boolean,
    painter: Painter = painterResource(id = R.drawable.baseline_person_24)
) {
    Column(
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 8.dp)
        ) {
            Image(
                // Should get the contacts avatar according to the userId
                painter = painter,
                contentDescription = "",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(180.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer),
            )
        }

        Text(
            text = "Jack",
            modifier = Modifier.padding(0.dp, 8.dp).fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge)

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 8.dp)
        ) {
            IconButton(
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                onClick = { /*TODO*/ }
            ) {
                Icon(imageVector = Icons.Filled.Textsms, contentDescription = "Start chatting")
            }
            if (haveFollowed) {
                IconButton(
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    onClick = { /*TODO*/ },
                ) {
                    Icon(imageVector = Icons.Filled.FavoriteBorder, contentDescription = "Follow")
                }
            } else {
                IconButton(
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    onClick = { /*TODO*/ }
                ) {
                    Icon(imageVector = Icons.Filled.Favorite, contentDescription = "Stop following")
                }
            }
        }


    }
}


@Preview(showSystemUi = true)
@Composable
fun ContactDetailScreenPreview() {
    CrossLoquiTheme {
        ContactDetailScreen(navController = rememberNavController())
    }
}