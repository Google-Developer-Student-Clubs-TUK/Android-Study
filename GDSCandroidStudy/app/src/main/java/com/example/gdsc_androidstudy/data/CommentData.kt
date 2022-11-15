package com.example.gdsc_androidstudy.data
@kotlinx.serialization.Serializable
data class CommentData(
    val postId: Int,
    val content: String,
    val userId: String
)
@kotlinx.serialization.Serializable
data class CommentResponse(
    val postId: Int,
    val nickname: String,
    val content: String,
    val userId: String,
    val createdAt: String
)