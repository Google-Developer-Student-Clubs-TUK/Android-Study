package com.example.gdsc_androidstudy.data // ktlint-disable package-name

data class PostData(
    val userId: String,
    val userProfileImage: String,
    val postContent: String,
    val postImage: List<String>,
    val postDate: String
)
