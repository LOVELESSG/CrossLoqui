package com.example.crossloqui.ui.contact

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crossloqui.data.User
import com.example.crossloqui.firebase.repositories.FirestoreRepository
import com.example.crossloqui.firebase.repositories.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactDetailViewModel @Inject constructor(private val firestoreRepository: FirestoreRepository): ViewModel() {
    var contactDetailUiState by mutableStateOf(ContactDetailUiState())

    val hasUser: Boolean
        get() = firestoreRepository.hasUser()
    val currentUserId: String
        get() = firestoreRepository.getCurrentUserId()


    fun getCurrentUserDetail() = viewModelScope.launch {
        firestoreRepository.getCurrentUser().collect {
            contactDetailUiState = contactDetailUiState.copy(currentUser = it)
        }
    }

    private fun getContactDetails(targetUserEmail: String) = viewModelScope.launch {
        firestoreRepository.getTargetUser(targetUserEmail).collect {
            contactDetailUiState = contactDetailUiState.copy(targetUser = it)
        }
    }

    fun loadContactDetails(targetUserEmail: String) {
        if (hasUser) {
            if (currentUserId.isNotBlank()) {
                getContactDetails(targetUserEmail = targetUserEmail)
            }
        } else {
            contactDetailUiState = contactDetailUiState.copy(
                targetUser = Resources.Error(
                    throwable = Throwable(message = "Not Login")
                ),
                currentUser = Resources.Error(
                    throwable = Throwable(message = "Not Login")
                )
            )
        }
    }

    fun addFriend(message: String) {
        if (hasUser) {
            firestoreRepository.createFriendRequest(
                senderName = contactDetailUiState.currentUser.data!!.name,
                receiverName = contactDetailUiState.targetUser.data!!.name,
                receiverId = contactDetailUiState.targetUser.data!!.id,
                message = message
            ) {
                contactDetailUiState = contactDetailUiState.copy(createRequestStatus = it)
            }
        }
    }
}


data class ContactDetailUiState(
    val targetUser: Resources<User> = Resources.Loading(),
    val currentUser: Resources<User> = Resources.Loading(),
    val createRequestStatus: Boolean = false
)