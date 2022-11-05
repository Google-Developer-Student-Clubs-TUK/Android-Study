package com.example.gdsc_androidstudy.main.post // ktlint-disable package-name

data class PostData(
    val userId: String,
    val userProfileImage: String,
    val postContent: String,
    val postImage: List<String>,
    val postDate: String
)
