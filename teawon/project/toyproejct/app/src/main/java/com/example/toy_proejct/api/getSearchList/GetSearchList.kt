package com.example.toy_proejct.api.getSearchList

@kotlinx.serialization.Serializable
data class GetSearchList(
    val productListDtoList: List<ProductListDto>,
    val totalNumber: Int
)