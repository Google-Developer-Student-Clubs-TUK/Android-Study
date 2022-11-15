package com.example.toy_proejct.data.product.detail

@kotlinx.serialization.Serializable
data class DetailDto(
    val image: String,
    val mallDtoInfo: List<MallDtoInfo>,
    val minimumPrice: Int,
    val title: String,
    val url: String
)


