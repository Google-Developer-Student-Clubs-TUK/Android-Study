package com.example.gdsc_androidstudy.network

import com.example.gdsc_androidstudy.data.*
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.*
import kotlinx.serialization.serializer

class DBRepository {
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
    val BASE_URL = "http://ec2-13-115-168-93.ap-northeast-1.compute.amazonaws.com:3000"

    suspend fun getPost(): List<PostResponse> {
        return client.get<List<PostResponse>>(BASE_URL + "/post/getPost")
    }

    suspend fun entryPost(postRequest: PostRequest): PostResponse {
        return client.post<PostResponse>(BASE_URL + "/post/entryPost") {
            body = postRequest
            contentType(ContentType.Application.Json)
        }
    }
    suspend fun getProfile(uId: String): User? {
        return client.get<User?>(BASE_URL + "/user/getProfile") {
            parameter("userId", uId)
        }
    }
    suspend fun joinUser(user: UserRequest): User {
        return client.get<User>(BASE_URL + "/user/join") {
            body = user
            contentType(ContentType.Application.Json)
        }
    }
    suspend fun getPostByUId(userId: String): List<PostResponse> {
        return client.get<List<PostResponse>>(BASE_URL + "/post/getPostByUId") {
            parameter("userId", userId)
        }
    }
    suspend fun getComment(postId: Int): List<CommentResponse> {
        return client.get(BASE_URL + "/comment/getCommentByPostId") {
            parameter("postId", postId)
        }
    }
    suspend fun entryComment(commentData: CommentData): Int {
        return client.post<Int>(BASE_URL + "/comment/entryComment") {
            body = commentData
            contentType(ContentType.Application.Json)
        }
    }
}
