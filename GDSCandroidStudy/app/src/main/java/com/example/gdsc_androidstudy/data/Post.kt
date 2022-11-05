package com.example.gdsc_androidstudy.data

data class Post(
    val postId: Int,
    val userId: String,
    val content: String,
    val createdAt: Int,
    val postImg: List<String>
)
