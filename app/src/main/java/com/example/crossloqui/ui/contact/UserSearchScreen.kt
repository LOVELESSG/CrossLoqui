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
import com.example.crossloqui.data.User
import com.example.crossloqui.navigation.Screen
import com.example.crossloqui.ui.theme.CrossLoquiTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase
import io.mockk.mockk

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun UserSearchScreen(auth: FirebaseAuth, navController: NavController) {
    var userId by remember {
        mutableStateOf("")
    }
    var active by remember {
        mutableStateOf(false)
    }
    var isFriend by remember {
        mutableStateOf(false)
    }
    var hasFollowed: Boolean
    val context = LocalContext.current
    val db = Firebase.firestore
    var currentUser: User? = null

    SearchBar(
        query = userId,
        onQueryChange = { userId = it },
        onSearch = {
            active = false
            // Get currentUser
            db.collection("users")
                .whereEqualTo("id", auth.currentUser?.uid)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    currentUser = documentSnapshot.toObjects<User>()[0]
                }
            //Check whether the target user is a friend of current user
            db.collection("users").document("${auth.currentUser?.uid}").collection("friends")
                .whereEqualTo("email", userId)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    val user = documentSnapshot.toObjects<User>()
                    val name = user[0].name
                    val email = user[0].email
                    isFriend = true
                    hasFollowed = currentUser?.followingUser?.contains(user[0].id) ?: false
                    //hasFollowed = user[0].followingUser.contains(user[0].id)
                    navController.navigate(
                        route = "${Screen.ContactDetail.route}/${isFriend}/${hasFollowed}"
                    )
                }
            //If target user is not a friend of current user, get the user's detail from users database
            if (!isFriend) {
                db.collection("users")
                    .whereEqualTo("email", userId)
                    .get()
                    .addOnSuccessListener { documentSnapshot ->
                        val user = documentSnapshot.toObjects<User>()
                        hasFollowed = currentUser?.followingUser?.contains(user[0].id) ?: false
                        navController.navigate(
                            route = "${Screen.ContactDetail.route}/${isFriend}/${hasFollowed}"
                        )
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(context, "No such user", Toast.LENGTH_LONG).show()
                    }
            }
            //Toast.makeText(context, "searching $userId", Toast.LENGTH_LONG).show()
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
    val fakeFirebaseAuth = mockk<FirebaseAuth>()

    CrossLoquiTheme {
        UserSearchScreen(fakeFirebaseAuth, navController = rememberNavController())
    }
}