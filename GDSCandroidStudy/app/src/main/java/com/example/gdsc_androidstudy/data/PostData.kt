package com.example.gdsc_androidstudy.data

import kotlinx.serialization.Serializable

// ktlint-disable package-name

@Serializable
data class PostRequest(
    val userId: String,
    val content: String
)

@Serializable
data class PostResponse(
    val userId: String,
    val postId: Int,
    val userProfileImage: String,
    val postContent: String,
    val createdAt: String
)
