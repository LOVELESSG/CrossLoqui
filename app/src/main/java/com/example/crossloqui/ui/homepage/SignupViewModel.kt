package com.example.crossloqui.ui.homepage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crossloqui.firebase.repositories.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository,
): ViewModel() {
    var signupUiState by mutableStateOf(SignupUiState())

    fun changeUserName(userName: String) {
        signupUiState = signupUiState.copy(userName = userName)
    }

    fun changeBio(bio: String) {
        signupUiState = signupUiState.copy(bio = bio)
    }

    fun changeBirthday(birthday: String) {
        signupUiState = signupUiState.copy(birthday = birthday)
    }

    fun changeGender(gender: String) {
        signupUiState = signupUiState.copy(gender = gender)
    }

    fun changeImageUri(imageUri: String) {
        signupUiState = signupUiState.copy(imageUri = imageUri)
    }

    fun changeImagePath(imagePath: String) {
        signupUiState = signupUiState.copy(imagePath = imagePath)
    }

    fun changeEmail(emailAddress: String) {
        signupUiState = signupUiState.copy(emailAddress = emailAddress)
    }

    fun changeEmailValid(emailAddressValid: String) {
        signupUiState = signupUiState.copy(emailAddressValid = emailAddressValid)
    }

    fun changePassword(password: String) {
        signupUiState = signupUiState.copy(password = password)
    }

    fun createNewUser(
        userName: String,
        email: String,
        bio: String,
        gender: String,
        birthday: String,
        registerDate: String,
        password: String,
        imagePath: String,
        navigateToHomepage: () -> Unit
    ) = viewModelScope.launch {
        firestoreRepository.createUser(
            userName = userName,
            email = email,
            bio = bio,
            gender = gender,
            birthday = birthday,
            registerDate = registerDate,
            password = password,
            imagePath = imagePath,
            navigateToHomepage = navigateToHomepage
        )
    }
}


data class SignupUiState(
    val emailAddress:String = "",
    val emailAddressValid: String = "",
    val genderOptions: List<String> = listOf("male", "female"),
    val birthday: String = "",
    val userName: String = "",
    val bio: String = "",
    val gender: String = "male",
    val imageUri: String = "",
    val imagePath: String = "",
    val password: String = ""
)