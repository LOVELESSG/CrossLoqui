package com.example.crossloqui.ui.homepage

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
import com.example.crossloqui.ui.theme.CrossLoquiTheme

@Composable
fun RegisterScreen(
    navController: NavController,
    signupViewModel: SignupViewModel
) {
    val signupUiState = signupViewModel.signupUiState

    RegisterContent(
        emailAddress = signupUiState.emailAddress,
        emailAddressValid = signupUiState.emailAddressValid,
        password = signupUiState.password,
        changeEmail = signupViewModel::changeEmail,
        changeEmailValid = signupViewModel::changeEmailValid,
        changePassword = signupViewModel::changePassword,
        navController = navController
    )
}

@Composable
fun RegisterContent(
    emailAddress: String,
    emailAddressValid: String,
    password: String,
    changeEmail: (String) -> Unit,
    changeEmailValid: (String) -> Unit,
    changePassword: (String) -> Unit,
    navController: NavController
) {
    var passwordVisible by remember {
        mutableStateOf(false)
    }
    val annotatedText = buildAnnotatedString { append("Go to Login Page") }
    val context = LocalContext.current

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
                text = "Create Account",
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
                onValueChange = { changeEmail(it) },
                label = { Text(text = "Email Address") },
                placeholder = { Text(text = "Input email address")},
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )
            TextField(
                value = emailAddressValid,
                onValueChange = { changeEmailValid(it) },
                label = { Text(text = "Validate Email Address") },
                placeholder = { Text(text = "Input email address again") },
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
                onValueChange = { changePassword(it) },
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
                    if (emailAddress.isEmpty())
                        Toast.makeText(context,"Please enter your email address", Toast.LENGTH_SHORT).show()
                    else if (password.isEmpty())
                        Toast.makeText(context,"Please enter your password", Toast.LENGTH_SHORT).show()
                    else if (password.length < 6)
                        Toast.makeText(context,"Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                    else if (emailAddress != emailAddressValid)
                        Toast.makeText(context,"Email address must be the same", Toast.LENGTH_SHORT).show()
                    else{
                        navController.navigate("account_info_screen")
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 8.dp)
            ) {
                Text(text = "Register")
            }
            ClickableText(
                text = annotatedText,
                onClick = {
                    navController.navigate("login_screen")
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp)
            )
        }


    }
}


@Preview(showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    CrossLoquiTheme {
        RegisterContent(
            navController = rememberNavController(),
            emailAddress = "",
            emailAddressValid = "",
            password = "",
            changeEmail = {},
            changeEmailValid = {},
            changePassword = {}
        )
    }
}