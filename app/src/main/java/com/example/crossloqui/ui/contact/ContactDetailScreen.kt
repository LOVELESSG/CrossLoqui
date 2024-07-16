package com.example.crossloqui.ui.contact

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Textsms
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
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
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
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
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        //Should judge whether the user has been followed by current user
        //val haveFollowed: Boolean
        ContactDetailContent(paddingValues, navController = navController, false)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetailContent(
    paddingValues: PaddingValues,
    navController: NavController,
    haveFollowed: Boolean,
    painter: Painter = painterResource(id = R.drawable.baseline_person_24)
) {
    var informationExpanded by remember {
        mutableStateOf(true)
    }
    val connection = remember {
        object : NestedScrollConnection {

        }
    }

    LazyColumn(modifier = Modifier.padding(paddingValues = paddingValues)) {
        item {
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
                modifier = Modifier
                    .padding(0.dp, 8.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge,
                maxLines = 1
            )

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

            if (informationExpanded) {
                Card(
                    onClick = { informationExpanded = !informationExpanded },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 8.dp)
                ) {
                    Row {
                        Text(
                            text = "Information",
                            modifier = Modifier
                                .padding(16.dp, 16.dp, 16.dp, 8.dp)
                                .weight(1f)
                        )
                        Icon(
                            imageVector = Icons.Filled.ExpandLess,
                            contentDescription = "Show more information",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(16.dp, 8.dp, 16.dp, 16.dp)
                        ) {
                            Icon(imageVector = Icons.Filled.Cake, contentDescription = "Birthday")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "1999/09/13")
                        }
                        if ("male" == "male"){
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(16.dp, 8.dp, 16.dp, 16.dp)
                            ) {
                                Icon(imageVector = Icons.Filled.Male, contentDescription = "Male")
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "Male")
                            }
                        } else {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(16.dp, 8.dp, 16.dp, 16.dp)
                            ) {
                                Icon(imageVector = Icons.Filled.Female, contentDescription = "Female")
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "Female")
                            }
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(16.dp, 8.dp, 16.dp, 16.dp)
                        ) {
                            Icon(imageVector = Icons.Filled.AlternateEmail, contentDescription = "Birthday")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Jack@mail.com")
                        }
                    }
                }
            } else {
                Card(
                    onClick = { informationExpanded = !informationExpanded },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Information",
                                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 8.dp)
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(16.dp, 8.dp, 16.dp, 16.dp)
                            ) {
                                Icon(imageVector = Icons.Filled.Cake, contentDescription = "Birthday")
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "1999/09/13")
                            }
                        }
                        Icon(
                            imageVector = Icons.Filled.ExpandMore,
                            contentDescription = "Show more information",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }

            Divider(modifier = Modifier.padding(0.dp, 8.dp))

            Text(
                text = "Recent Post",
                modifier = Modifier.padding(32.dp, 8.dp),
                //style = MaterialTheme.typography.titleLarge
            )
        }

        // Need to show the recent post
        items(9) {
            Card(
                modifier = Modifier
                    .padding(16.dp, 8.dp)
                    .height(160.dp)
                    .fillMaxWidth(),
                onClick = {}
            ) {
                Text(text = "A Post", modifier = Modifier.padding(16.dp))
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