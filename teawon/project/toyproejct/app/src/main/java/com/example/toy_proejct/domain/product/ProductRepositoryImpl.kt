package com.example.toy_proejct.domain.product

import com.example.toy_proejct.data.product.detail.DetailDto
import com.example.toy_proejct.data.product.list.GetSearchList
import io.ktor.client.*
import io.ktor.client.request.*

class ProductRepositoryImpl(private val client: HttpClient): ProductRepository {
    override suspend fun fetchProductList(keyword: String): GetSearchList =
        client.get("http://3.39.75.19:8080/api/v1/crawler/search/products?word=$keyword")

    override suspend fun fetchProductDetail(url: String): DetailDto =
        client.get("http://3.39.75.19:8080/api/v1/crawler/search/product?url=${url}")
}