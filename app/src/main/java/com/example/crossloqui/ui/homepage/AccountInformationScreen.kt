package com.example.crossloqui.ui.homepage

import android.content.res.Configuration
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.example.compose.CrossLoquiTheme
import com.example.crossloqui.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun AccountInformationScreen(
    navController: NavController,
    signupViewModel: SignupViewModel
) {
    val signupUiState = signupViewModel.signupUiState
    val painter = rememberAsyncImagePainter(
        signupUiState.imageUri.ifEmpty { R.drawable.baseline_person_24 }
    )

    AccountInformationContent(
        painter = painter,
        imagePath = signupUiState.imagePath,
        userName = signupUiState.userName,
        birthday = signupUiState.birthday,
        bio = signupUiState.bio,
        email = signupUiState.emailAddress,
        password = signupUiState.password,
        changeUserName = signupViewModel::changeUserName,
        changeBio = signupViewModel::changeBio,
        changeBirthday = signupViewModel::changeBirthday,
        changeGender = signupViewModel::changeGender,
        changeImageUri = signupViewModel::changeImageUri,
        changeImagePath = signupViewModel::changeImagePath,
        createNewUser = signupViewModel::createNewUser,
        navigateToHomepage = { navController.navigate("home_screen") }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountInformationContent(
    painter: AsyncImagePainter,
    imagePath: String,
    userName: String,
    birthday: String,
    bio: String,
    email: String,
    password: String,
    genderOptions: List<String> = listOf("male", "female"),
    changeUserName: (String) -> Unit,
    changeBio: (String) -> Unit,
    changeBirthday: (String) -> Unit,
    changeGender: (String) -> Unit,
    changeImageUri: (String) -> Unit,
    changeImagePath: (String) -> Unit,
    createNewUser: (String,String,String,String,String,String,String,String, () -> Unit) -> Unit,
    navigateToHomepage: () -> Unit
) {
    val context = LocalContext.current
    var expanded by remember {
        mutableStateOf(false)
    }
    var selectedGender by remember {
        mutableStateOf(genderOptions[0])
    }
    val launcher =
        rememberLauncherForActivityResult(contract = CropImageContract()) {result ->
            result.uriContent?.let {
                changeImageUri(it.toString())
                changeImagePath(result.getUriFilePath(context).toString())
            }
        }
    val cropOptions = CropImageContractOptions(null, CropImageOptions(imageSourceIncludeCamera = false))

    Scaffold { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
                        .weight(3f)
                        .fillMaxWidth()
                        .padding(start = 32.dp)
                ) {
                    Text(
                        text = "Account Details",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                LazyColumn (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 32.dp)
                        .weight(7f)
                ) {

                    item {
                        Column(
                            modifier = Modifier
                        ) {
                            Card(
                                shape = CircleShape,
                                modifier = Modifier
                                    .size(100.dp),
                            ) {
                                Image(
                                    painter = painter,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(100.dp)
                                        .clickable {
                                            launcher.launch(cropOptions)
                                        },
                                    contentScale = ContentScale.Crop
                                )
                            }
                            OutlinedTextField(
                                value = userName,
                                onValueChange = { changeUserName(it) },
                                label = { Text(text = "User Name") },
                                placeholder = { Text(text = "Input your user name") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp)
                            )

                            Row(
                                modifier = Modifier
                                    .padding(top = 8.dp)
                                    .fillMaxWidth()
                            ) {
                                TextField(
                                    value = birthday,
                                    onValueChange = { changeBirthday(it) },
                                    label = { Text(text = "Birthday") },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.weight(1f)
                                )
                                ExposedDropdownMenuBox(
                                    expanded = expanded,
                                    onExpandedChange = { expanded = it },
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                        .weight(1f)
                                ) {
                                    TextField(
                                        modifier = Modifier.menuAnchor(),
                                        value = selectedGender,
                                        onValueChange = {changeGender(selectedGender)},
                                        readOnly = true,
                                        singleLine = true,
                                        label = { Text(text = "Gender") },
                                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded =expanded) },
                                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                                    )
                                    ExposedDropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = { expanded = false }
                                    ) {
                                        genderOptions.forEach { option ->
                                            DropdownMenuItem(
                                                text = { Text(text = option) },
                                                onClick = {
                                                    selectedGender = option
                                                    expanded = false
                                                },
                                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                            )
                                        }
                                    }
                                }
                            }

                            OutlinedTextField(
                                value = bio,
                                onValueChange = { changeBio(it) },
                                label = { Text(text = "Bio") },
                                placeholder = { Text(text = "Introduce yourself") },
                                minLines = 3,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp)
                            )
                            Button(
                                onClick = {
                                    if (isValidBirthdayFormat(inputBirthday = birthday) || birthday == "") {
                                        try {
                                            // Create new user
                                            createNewUser(
                                                userName,
                                                email,
                                                bio,
                                                selectedGender,
                                                birthday,
                                                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")),
                                                password,
                                                imagePath,
                                                navigateToHomepage
                                            )

                                        } catch (_: Exception) {
                                            Toast.makeText(context, "Registration failed", Toast.LENGTH_SHORT)
                                                .show()
                                        }
                                    } else {
                                        Toast.makeText(context, "Input correct birthday format like 19701201", Toast.LENGTH_LONG).show()
                                    }
                                },
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(top = 8.dp)
                            ) {
                                Text(text = "Register")
                            }
                        }
                    }

                }
            }
        }
    }
}

fun isValidBirthdayFormat(inputBirthday: String): Boolean {
    val regex = Regex("^\\d{8}$")
    return regex.matches(inputBirthday)
}


@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AccountInformationScreenPreview() {

    CrossLoquiTheme {
        AccountInformationContent(
            painter = rememberAsyncImagePainter(R.drawable.baseline_person_24),
            imagePath = "",
            userName = "Jack",
            birthday = "19970912",
            bio = "Hi",
            email = "",
            password = "",
            changeUserName = {},
            changeBio = {},
            changeGender = {},
            changeBirthday = {},
            changeImageUri = {},
            changeImagePath = {},
            createNewUser = { _, _, _, _, _, _, _, _, _ -> },
            navigateToHomepage = {}
        )
    }
}