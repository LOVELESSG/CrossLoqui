package com.example.crossloqui.ui.contact

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crossloqui.data.FriendRequest
import com.example.crossloqui.firebase.repositories.FirestoreRepository
import com.example.crossloqui.firebase.repositories.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewFriendViewModel @Inject constructor(private val firestoreRepository: FirestoreRepository) : ViewModel() {
    var newFriendUiState by mutableStateOf(NewFriendUiState())

    val hasUser: Boolean
        get() = firestoreRepository.hasUser()
    val currentUserId: String
        get() = firestoreRepository.getCurrentUserId()

    private fun getFriendRequest(currentUserId: String) = viewModelScope.launch {
        firestoreRepository.getNewFriendList(currentUserId).collect {
            newFriendUiState = newFriendUiState.copy(friendRequestList = it)
        }
    }

    fun loadFriendRequest() {
        if (hasUser) {
            if (currentUserId.isNotBlank()) {
                getFriendRequest(currentUserId)
            }
        } else {
            newFriendUiState = newFriendUiState.copy(friendRequestList = Resources.Error(
                throwable = Throwable(message = "Not Login")
            ))
        }
    }
}


data class NewFriendUiState(
    val friendRequestList: Resources<List<FriendRequest>> = Resources.Loading()
)