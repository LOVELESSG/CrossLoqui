package com.example.crossloqui.data

data class FriendRequest (
    val senderId: String?,
    val senderName: String?,
    val receiverId: String,
    val receiverName: String,
    val message: String,
    val addTime: String,
    val members: MutableList<String?> = mutableListOf(),
) {
    constructor() : this("", "", "", "", "", "")
}