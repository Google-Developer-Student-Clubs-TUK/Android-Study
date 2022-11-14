package com.example.toy_proejct.scenarios.home.data

@kotlinx.serialization.Serializable
data class GetSearchList(
    val productListDtoList: List<ProductListDto>,
    val totalNumber: Int
)