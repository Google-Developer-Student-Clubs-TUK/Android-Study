package com.example.toy_proejct.di

import com.example.toy_proejct.domain.product.ProductRepository
import com.example.toy_proejct.domain.product.ProductRepositoryImpl
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*

object DataModule {
    private val client: HttpClient = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                kotlinx.serialization.json.Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    val productRepository: ProductRepository = ProductRepositoryImpl(client)
}