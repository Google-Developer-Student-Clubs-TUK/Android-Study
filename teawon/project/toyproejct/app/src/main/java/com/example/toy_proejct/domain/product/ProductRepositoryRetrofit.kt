package com.example.toy_proejct.domain.product

import com.example.toy_proejct.data.product.detail.DetailDto
import com.example.toy_proejct.data.product.list.GetSearchList
import io.ktor.client.*

class ProductRepositoryRetrofit(private val client: HttpClient): ProductRepository {
    override suspend fun fetchProductList(keyword: String): GetSearchList {
        TODO("Not yet implemented")
    }

    override suspend fun fetchProductDetail(url: String): DetailDto {
        TODO("Not yet implemented")
    }
}