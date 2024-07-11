package com.example.crossloqui.data

data class User (
    val name: String,
    val email: String,
    val bio: String,
    val id: String?,
    val followingCount: Int,
    val followerCount: Int,
    val followingUser: MutableList<String> = mutableListOf(),
    val followerUser: MutableList<String> = mutableListOf()
)