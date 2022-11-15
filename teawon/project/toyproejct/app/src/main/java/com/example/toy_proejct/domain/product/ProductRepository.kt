package com.example.toy_proejct.domain.product

import com.example.toy_proejct.data.product.detail.DetailDto
import com.example.toy_proejct.data.product.list.GetSearchList

interface ProductRepository {
    suspend fun fetchProductList(keyword: String): GetSearchList
    suspend fun fetchProductDetail(url: String): DetailDto
}