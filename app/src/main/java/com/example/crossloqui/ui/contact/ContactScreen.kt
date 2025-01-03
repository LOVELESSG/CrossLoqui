package com.example.crossloqui.ui.contact

import android.app.Activity
import android.content.res.Configuration
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.compose.CrossLoquiTheme
import com.example.crossloqui.R
import com.example.crossloqui.components.CrossLoquiScaffold
import com.example.crossloqui.data.User
import com.example.crossloqui.navigation.Screen

@Composable
fun ContactScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val activity = (LocalContext.current as? Activity)
    val sampleContacts = mutableListOf(
        User("Cindy", "cindy@gmail.com", "I'm Cindy", "24fgerg", "Female", "19990909","19990909",123321, 2331234),
        User("Jack", "jack@gmail.com", "good", "24fgerg", "Female", "19990909","19990909",124521, 8733134),
        User("White", "w@gmail.com", "what happen?", "24fgerg", "Female", "19990909","19990909",123, 3434),
        User("Rose", "rose@gmail.com", "see you", "24fgerg", "Female", "19990909","19990909",123321, 1234),
        User("Singer", "sing@gmail.com", "a test to test a very very long bio and hope it will be fold into one line and be shown in a correct way like what we supposed", "24fgerg", "Female", "19990909","19990909",1231, 34),
        User("Youtube official", "you@tube.com", "happy happy happy", "24fgerg", "Female", "19990909","19990909",123321, 23),
        User("W!!!", "dfbgtrio@gmail.com", "??????", "24fgerg", "Male", "19990909","19990909",1673321, 21234),
        User("Cool", "cool@cool.com", "cool", "24fgerg", "Male", "19990909","19990909",3321, 234),
        )
    val sortedItems = sampleContacts.sortedBy { it.name }
    CrossLoquiScaffold(title = "Contacts", navController = navController) { paddingValues ->
        //获取朋友列表
        //遍历列表
        //项目.名称, 项目.bio, 项目.头像 = 列表项
        LazyColumn(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            item {
                ListItem(
                    headlineContent = { Text(
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        text = "New Friend"
                    ) },
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
                    modifier = Modifier.clickable { navController.navigate(Screen.FriendRequestScreen.route) }
                )
                HorizontalDivider()
            }
            items(sortedItems.size){item ->
                ListItem(
                    headlineContent = { Text(
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        text = sortedItems[item].name
                    ) },
                    supportingContent = { Text(
                        maxLines = 1,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        overflow = TextOverflow.Ellipsis,
                        text = sortedItems[item].bio
                    ) },
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
                )
                ListItem(
                    headlineContent = { Text(
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        text = sortedItems[item].name
                    ) },
                    supportingContent = { Text(
                        maxLines = 1,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        overflow = TextOverflow.Ellipsis,
                        text = sortedItems[item].bio
                    ) },
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
                    modifier = Modifier.clickable {
                        Toast.makeText(context, "click", Toast.LENGTH_SHORT).show()
                    }
                )
            }
            item {
                Spacer(modifier = Modifier.height(paddingValues.calculateBottomPadding()))
            }
        }
    }

    BackHandler {
        activity?.finish()
    }
}


/*@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)*/
@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ContactScreenPreview() {
    CrossLoquiTheme {
        ContactScreen(navController = rememberNavController())
    }
}