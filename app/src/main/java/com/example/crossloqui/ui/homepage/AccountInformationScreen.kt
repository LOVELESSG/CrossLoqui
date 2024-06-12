package com.example.crossloqui.ui.homepage

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import com.example.crossloqui.ui.theme.CrossLoquiTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import io.mockk.mockk
import java.io.File

@Composable
fun AccountInformationScreen(
    navController: NavController,
    auth: FirebaseAuth,
    email: String,
    password: String
) {

    val context = LocalContext.current

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
                text = "Account Details",
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
                                    val user = hashMapOf(
                                        "name" to userName,
                                        //"image" to encodedImage,
                                        "email" to email,
                                        "bio" to bio
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
                        Toast.makeText(context, "Registration failed", Toast.LENGTH_SHORT).show()
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


@Preview(showSystemUi = true)
@Composable
fun AccountInformationScreenPreview() {
    val fakeFirebaseAuth = mockk<FirebaseAuth>()
    CrossLoquiTheme {
        AccountInformationScreen(
            navController = rememberNavController(),
            fakeFirebaseAuth,
            "test@test.com",
            "123456"
        )
    }
}