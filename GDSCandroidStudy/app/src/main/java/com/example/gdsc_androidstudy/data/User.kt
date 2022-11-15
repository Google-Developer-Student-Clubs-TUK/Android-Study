package com.example.gdsc_androidstudy.data

@kotlinx.serialization.Serializable
data class UserRequest(
    val userId: String,
    val nickname: String,
    val email: String,
    val profileImg: String?
)
@kotlinx.serialization.Serializable
data class User(
    val userId: String,
    val nickname: String,
    val email: String,
    val profileImg: String?,
    val createdAt: String
)
