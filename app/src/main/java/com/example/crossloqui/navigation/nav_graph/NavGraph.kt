package com.example.crossloqui.navigation.nav_graph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.example.crossloqui.ui.homepage.SignupViewModel
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
        ) { backStackEntry ->
            val signupViewModel: SignupViewModel = hiltViewModel(backStackEntry)
            RegisterScreen(
                navController = navController,
                signupViewModel = signupViewModel
            )
        }
        composable(
            route = Screen.AccountInfo.route
        ) {
            val signupViewModel: SignupViewModel =
                if (navController.previousBackStackEntry != null) hiltViewModel(
                    navController.previousBackStackEntry!!
                ) else hiltViewModel()

            AccountInformationScreen(
                navController = navController,
                signupViewModel = signupViewModel
            )
        }
        composable(
            route = Screen.ContactDetail.route+"/{isFriend}/{targetEmail}"
        ) {
            val isFriend = it.arguments!!.getString("isFriend").toBoolean()
            val targetEmail = it.arguments!!.getString("targetEmail").toString()
            ContactDetailScreen(
                isFriend = isFriend,
                email = targetEmail,
            )
        }
        composable(
            route = Screen.UserSearchScreen.route
        ) {
            UserSearchScreen(navController = navController)
        }
        composable(
            route = Screen.FriendRequestScreen.route
        ) {
            NewFriendScreen(navController = navController)
        }
    }
}