package com.example.crossloqui.data

data class User (
    val name: String,
    val email: String,
    val bio: String,
    val id: String,
    val gender: String,
    val birthday: String,
    val registerDate: String,
    val followingCount: Int,
    val followerCount: Int,
    val followingUser: MutableList<String> = mutableListOf(),
    val followerUser: MutableList<String> = mutableListOf(),
    val friendList: MutableList<String> = mutableListOf()
) {
    constructor() : this("","", "","","","","",0,0)
}