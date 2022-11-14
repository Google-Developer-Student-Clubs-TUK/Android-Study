package com.example.toy_proejct.scenarios.home.data

@kotlinx.serialization.Serializable
data class ProductListDto(
    val imageUrl: String,
    val minimumPrice: Int,
    val title: String,
    val url: String
)