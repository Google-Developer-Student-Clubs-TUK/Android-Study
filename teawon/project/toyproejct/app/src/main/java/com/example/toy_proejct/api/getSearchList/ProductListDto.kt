package com.example.toy_proejct.api.getSearchList

@kotlinx.serialization.Serializable
data class ProductListDto(
    val imageUrl: String,
    val minimumPrice: Int,
    val title: String,
    val url: String
)