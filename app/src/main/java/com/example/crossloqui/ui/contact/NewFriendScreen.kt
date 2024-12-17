package com.example.crossloqui.ui.contact

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.compose.CrossLoquiTheme
import com.example.crossloqui.R
import com.example.crossloqui.components.CrossLoquiScaffold
import com.example.crossloqui.firebase.repositories.Resources

@Composable
fun NewFriendScreen(
    navController: NavController,
    newFriendViewModel: NewFriendViewModel = hiltViewModel()
) {
    val newFriendUiState = newFriendViewModel.newFriendUiState

    LaunchedEffect(key1 = Unit) {
        newFriendViewModel.loadFriendRequest()
    }

    CrossLoquiScaffold(title = "Friend Request", navController = navController) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            when (newFriendUiState.friendRequestList) {
                is Resources.Loading -> {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize(align = Alignment.Center)
                        )
                    }

                }
                is Resources.Success -> {
                    items(newFriendUiState.friendRequestList.data ?: emptyList()) { friendRequest ->
                        val requestStatus = friendRequest.requestStatus
                        if (friendRequest.senderId == newFriendViewModel.currentUserId) {
                            ListItem(
                                headlineContent = { Text(friendRequest.receiverName) },
                                supportingContent = { Text(friendRequest.message) },
                                leadingContent = {
                                    Image(
                                        painter = painterResource(id = R.drawable.baseline_person_24),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .size(40.dp)
                                            .background(MaterialTheme.colorScheme.primaryContainer),
                                    )
                                },
                                trailingContent = {
                                    when(requestStatus) {
                                        "accepted" -> Text("Accepted")
                                        "rejected" -> Text("Rejected")
                                        "progressing" -> Text("Request Send")
                                    }
                                }
                            )
                        } else {
                            ListItem(
                                headlineContent = { Text(friendRequest.receiverName) },
                                supportingContent = { Text(friendRequest.message) },
                                leadingContent = {
                                    Image(
                                        painter = painterResource(id = R.drawable.baseline_person_24),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .size(40.dp)
                                            .background(MaterialTheme.colorScheme.primaryContainer),
                                    )
                                },
                                trailingContent = {
                                    when(requestStatus) {
                                        "accepted" -> Text("Accepted")
                                        "rejected" -> Text("Rejected")
                                        "progressing" ->
                                            Row {
                                                IconButton(onClick = {
                                                    friendRequest.senderId?.let {
                                                        newFriendViewModel.acceptFriendRequest(friendRequest.receiverId, it)
                                                        Log.d("click agree", "accept request")
                                                    }
                                                }) {
                                                    Icon(Icons.Default.Check, "")
                                                }
                                                IconButton(onClick = {}) {
                                                    Icon(Icons.Default.Close, "")
                                                }
                                            }
                                    }
                                }
                            )
                        }
                    }
                }
                else -> {
                    item {
                        Text(
                            text = newFriendUiState.friendRequestList.throwable?.localizedMessage ?: "Error",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }

    }

}


@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun NewFriendScreenPreview() {
    CrossLoquiTheme {
        LazyColumn {
            item {
                ListItem(
                    headlineContent = { Text("Jack") },
                    supportingContent = { Text("Hi, how are you?") },
                    leadingContent = {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_person_24),
                            contentDescription = "",
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(40.dp)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                        )
                    },
                    trailingContent = {
                        Row(
                            modifier = Modifier
                                .padding(end = 0.dp)
                        ) {
                            IconButton(onClick = {}) {
                                Icon(Icons.Default.Check, "")
                            }
                            IconButton(onClick = {}) {
                                Icon(Icons.Default.Close, "")
                            }
                        }
                    }
                )

                ListItem(
                    headlineContent = { Text("Jack") },
                    supportingContent = { Text("Hi, how are you?") },
                    leadingContent = {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_person_24),
                            contentDescription = "",
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(40.dp)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                        )
                    },
                    trailingContent = {
                        Row(
                            modifier = Modifier
                                .padding(end = 0.dp)
                        ) {
                            IconButton(onClick = {}) {
                                Icon(Icons.Default.Check, "")
                            }
                            IconButton(onClick = {}) {
                                Icon(Icons.Default.Close, "")
                            }
                        }
                    }
                )

                ListItem(
                    headlineContent = { Text("Jack") },
                    supportingContent = { Text("Hi, how are you?") },
                    leadingContent = {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_person_24),
                            contentDescription = "",
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(40.dp)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                        )
                    },
                    trailingContent = {
                        Row(
                            modifier = Modifier
                                .padding(end = 0.dp)
                        ) {
                            Text("Request Sent")
                        }
                    }
                )

                ListItem(
                    headlineContent = { Text("Jack") },
                    supportingContent = { Text("Hi, how are you?") },
                    leadingContent = {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_person_24),
                            contentDescription = "",
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(40.dp)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                        )
                    },
                    trailingContent = {
                        Row(
                            modifier = Modifier
                                .padding(end = 0.dp)
                        ) {
                            IconButton(onClick = {}) {
                                Icon(Icons.Default.Check, "")
                            }
                            IconButton(onClick = {}) {
                                Icon(Icons.Default.Close, "")
                            }
                        }
                    }
                )
            }
        }
    }
}