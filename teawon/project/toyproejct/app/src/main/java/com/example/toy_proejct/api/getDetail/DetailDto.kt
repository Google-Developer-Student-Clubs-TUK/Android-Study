package com.example.toy_proejct.api.getDetail

@kotlinx.serialization.Serializable
data class DetailDto(
    val image: String,
    val mallDtoInfo: List<MallDtoInfo>,
    val minimumPrice: Int,
    val title: String,
    val url: String
)


