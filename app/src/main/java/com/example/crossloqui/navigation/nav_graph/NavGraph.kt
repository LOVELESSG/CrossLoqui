package com.example.crossloqui.navigation.nav_graph

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.crossloqui.navigation.Screen
import com.example.crossloqui.ui.contact.ContactDetailScreen
import com.example.crossloqui.ui.contact.ContactScreen
import com.example.crossloqui.ui.contact.NewFriendScreen
import com.example.crossloqui.ui.contact.UserSearchScreen
import com.example.crossloqui.ui.conversation.ConversationScreen
import com.example.crossloqui.ui.homepage.AccountInformationScreen
import com.example.crossloqui.ui.homepage.HomepageScreen
import com.example.crossloqui.ui.homepage.LoginScreen
import com.example.crossloqui.ui.homepage.RegisterScreen
import com.example.crossloqui.ui.post.PostScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    auth: FirebaseAuth,
    startScreen: String
) {
    NavHost(
        navController = navController,
        startDestination = startScreen
    ) {
        composable(
            route = Screen.Home.route
        ) {
            //home screen
            HomepageScreen(navController = navController)
        }
        composable(
            route = Screen.Post.route
        ) {
            //post screen
            PostScreen(navController = navController)
        }
        composable(
            route = Screen.Contact.route
        ) {
            //contact screen
            ContactScreen(navController = navController)
        }
        composable(
            route = Screen.Conversation.route
        ) {
            ConversationScreen(navController = navController)
        }
        composable(
            route = Screen.Login.route
        ) {
            LoginScreen(navController = navController, auth = auth)
        }
        composable(
            route = Screen.Register.route
        ) {
            RegisterScreen(navController = navController, auth = auth)
        }
        composable(
            route = Screen.AccountInfo.route+"/{email}/{password}"
        ) {
            val email = it.arguments!!.getString("email")
            val password = it.arguments!!.getString("password")

            AccountInformationScreen(
                navController = navController,
                auth = auth,
                email = email!!,
                password = password!!
            )
        }
        composable(
            route = Screen.ContactDetail.route+"/{isFriend}/{hasFollowed}/{name}/{email}/{userId}/{gender}/{birthday}/{followerCount}/{followingCount}"
        ) {
            val isFriend = it.arguments!!.getString("isFriend").toBoolean()
            val hasFollowed = it.arguments!!.getString("hasFollowed").toBoolean()
            val name = it.arguments!!.getString("name").toString()
            val email = it.arguments!!.getString("email").toString()
            val userId = it.arguments!!.getString("userId").toString()
            val gender = it.arguments!!.getString("gender").toString()
            val birthday = it.arguments!!.getString("birthday").toString()
            val followerCount = it.arguments!!.getString("followerCount")!!.toInt()
            val followingCount = it.arguments!!.getString("followingCount")!!.toInt()
            ContactDetailScreen(
                isFriend = isFriend,
                hasFollowed = hasFollowed,
                name = name,
                email = email,
                userId = userId,
                gender = gender,
                birthday = birthday,
                followerCount = followerCount,
                followingCount = followingCount,
                navController = navController
            )
        }
        composable(
            route = Screen.UserSearchScreen.route
        ) {
            UserSearchScreen(auth = auth, navController = navController)
        }
        composable(
            route = Screen.FriendRequestScreen.route
        ) {
            NewFriendScreen(navController = navController)
        }
    }
}