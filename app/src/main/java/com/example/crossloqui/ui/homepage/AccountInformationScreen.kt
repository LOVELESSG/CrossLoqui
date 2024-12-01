package com.example.crossloqui.ui.homepage

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.example.crossloqui.R
import com.example.crossloqui.data.User
import com.example.crossloqui.ui.theme.CrossLoquiTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import io.mockk.mockk
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountInformationScreen(
    navController: NavController,
    auth: FirebaseAuth,
    email: String,
    password: String
) {

    val context = LocalContext.current

    var expanded by remember {
        mutableStateOf(false)
    }
    val genderOptions = listOf("Male", "Female")
    var selectedGender by remember {
        mutableStateOf(genderOptions[0])
    }

    var birthday by remember {
        mutableStateOf("")
    }

    var userName by remember {
        mutableStateOf("")
    }
    var bio by remember {
        mutableStateOf("")
    }
    var imageUri by rememberSaveable {
        mutableStateOf("")
    }
    var imagePath by rememberSaveable {
        mutableStateOf("")
    }
    val painter = rememberAsyncImagePainter(
        imageUri.ifEmpty { R.drawable.baseline_person_24 }
    )
    val launcher =
        rememberLauncherForActivityResult(contract = CropImageContract()) {result ->
            result.uriContent?.let {
                imageUri = it.toString()
                imagePath = result.getUriFilePath(context).toString()
            }
        }
    val cropOptions = CropImageContractOptions(null, CropImageOptions(imageSourceIncludeCamera = false))
    val db = FirebaseFirestore.getInstance()
    val storage = Firebase.storage
    val storageRef = storage.reference


    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            //.padding(horizontal = 32.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    //.weight(4f)
                    //.align(Alignment.Start)
            ) {
                Text(
                    text = "Account Details",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }

        item {
            Column(
                modifier = Modifier
                    //.weight(6f)
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
                    onValueChange = { userName = it },
                    label = { Text(text = "User Name") },
                    placeholder = { Text(text = "Input your user name") },
                    //isError = userName.isEmpty(),
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
                        onValueChange = { birthday = it },
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
                            onValueChange = {},
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
                    onValueChange = { bio = it },
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
                                auth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            val file = Uri.fromFile(File(imagePath))
                                            val avatarRef = storageRef.child("avatars/${auth.currentUser?.uid}")
                                            val uploadTask = avatarRef.putFile(file)
                                            uploadTask.addOnFailureListener {
                                                Toast.makeText(
                                                    context,
                                                    "Upload failed",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }.addOnSuccessListener {
                                                Toast.makeText(
                                                    context,
                                                    "Registration successful",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                            val user = User(
                                                name = userName,
                                                email = email,
                                                bio = bio,
                                                id = auth.currentUser!!.uid,
                                                gender = selectedGender,
                                                birthday = birthday,
                                                registerDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")),
                                                followingCount = 0,
                                                followerCount = 0
                                            )
                                            db.collection("users")
                                                .add(user)
                                            navController.navigate("home_screen")
                                        } else {
                                            val e = task.exception
                                            if (e is FirebaseAuthUserCollisionException) {
                                                Toast.makeText(
                                                    context,
                                                    "Email address already registered",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    "Registration failed",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    }
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

fun isValidBirthdayFormat(inputBirthday: String): Boolean {
    val regex = Regex("^\\d{8}$")
    return regex.matches(inputBirthday)
}


@Preview(showSystemUi = true)
@Composable
fun AccountInformationScreenPreview() {

    CrossLoquiTheme {
        val fakeFirebaseAuth = mockk<FirebaseAuth>()
        AccountInformationScreen(
            navController = rememberNavController(),
            fakeFirebaseAuth,
            "test@test.com",
            "123456"
        )
    }
}