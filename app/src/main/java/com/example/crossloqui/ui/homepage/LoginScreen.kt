package com.example.crossloqui.ui.homepage

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.compose.CrossLoquiTheme
import com.google.firebase.auth.FirebaseAuth
import io.mockk.mockk

@Composable
fun LoginScreen(
    navController: NavController,
    auth: FirebaseAuth
) {
    var emailAddress by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var passwordVisible by remember {
        mutableStateOf(false)
    }
    val annotatedText = buildAnnotatedString { append("Create new account") }
    val context = LocalContext.current

    Scaffold { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(4f)
                        .align(Alignment.Start)
                ) {
                    Text(
                        text = "Welcome back",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(6f)
                ) {
                    TextField(
                        value = emailAddress,
                        onValueChange = { emailAddress = it },
                        label = { Text(text = "Email Address") },
                        placeholder = { Text(text = "Input email address") },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text(text = "Password") },
                        singleLine = true,
                        placeholder = { Text(text = "Enter your password") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            val image = if (passwordVisible)
                                Icons.Filled.VisibilityOff
                            else Icons.Filled.Visibility

                            val iconDescription = if (passwordVisible) "Hide password" else "Show password"

                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(imageVector = image, contentDescription = iconDescription)
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )

                    Button(
                        onClick = {
                            auth.signInWithEmailAndPassword(emailAddress, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        navController.navigate("home_screen")
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Authentication failed.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 8.dp)
                    ) {
                        Text(text = "Login")
                    }
                    ClickableText(
                        text = annotatedText,
                        onClick = {
                            navController.navigate("register_screen")
                        },
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 16.dp)
                    )
                }
            }
        }
    }
}

@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoginScreenPreview() {
    CrossLoquiTheme {
        val fakeFirebaseAuth = mockk<FirebaseAuth>()
        LoginScreen(navController = rememberNavController(), fakeFirebaseAuth)
    }
}