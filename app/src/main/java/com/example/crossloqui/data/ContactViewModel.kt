package com.example.crossloqui.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ContactViewModel (): ViewModel() {
    var state by mutableStateOf(ContactsState())
        private set

    init {
        getAllContacts()
    }

    // Get all contacts of current user
    private fun getAllContacts() {
        viewModelScope.launch {
            // Get contacts from server
            // 需要从服务器返回一个联系人的list
        }
    }

    // Add another user to current user's contacts list
    fun addToContacts(){}

    // Delete user from current user's contact list
    fun deleteFromContacts() {}
}


data class ContactsState(
    val contact: List<User> = emptyList(),
)