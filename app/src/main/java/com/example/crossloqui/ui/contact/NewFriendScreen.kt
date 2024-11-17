package com.example.crossloqui.ui.contact

import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.crossloqui.R
import com.example.crossloqui.components.CrossLoquiScaffold
import com.example.crossloqui.data.FriendRequest
import com.example.crossloqui.ui.theme.CrossLoquiTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase

@Composable
fun NewFriendScreen(navController: NavController) {

    val db = FirebaseFirestore.getInstance()
    val auth = Firebase.auth
    val currentUserId = auth.currentUser?.uid
    val requestItem = remember { mutableStateListOf<FriendRequest>() }
    var isLoading by remember { mutableStateOf(true) }

    CrossLoquiScaffold(title = "Friend Request", navController = navController) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            if (isLoading) {
                item {
                    Box (
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                }
            } else {
                Log.d("newFriendArrayTest", requestItem.size.toString())
                items(requestItem.size) { item ->
                    if (requestItem[item].senderId == currentUserId) {
                        ListItem(
                            headlineContent = { Text(requestItem[item].receiverName) },
                            supportingContent = { Text(requestItem[item].message) },
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
                                Text("Request Sent")
                            }
                        )
                    } else {
                        ListItem(
                            headlineContent = { Text(requestItem[item].receiverName) },
                            supportingContent = { Text(requestItem[item].message) },
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
                                Row {
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

        LaunchedEffect(Unit) {
            if (currentUserId != null) {
                db.collection("friendRequest")
                    .whereArrayContains("members", currentUserId)
                    .get()
                    .addOnSuccessListener { documentSnapshot ->
                        requestItem.addAll(documentSnapshot.toObjects<FriendRequest>().sortedByDescending { SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(it.addTime) })
                        isLoading = false
                    }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun NewFriendScreenPreview() {
    CrossLoquiTheme {
        //NewFriendScreen(navController = rememberNavController())
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