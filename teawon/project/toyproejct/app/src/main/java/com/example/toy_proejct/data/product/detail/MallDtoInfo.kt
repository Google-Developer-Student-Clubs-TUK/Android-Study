package com.example.toy_proejct.data.product.detail
@kotlinx.serialization.Serializable
data class MallDtoInfo(
    val delivery: Int,
    val interestFree: String,
    val link: String,
    val name: String,
    val paymentOption: String,
    val price: Int
)