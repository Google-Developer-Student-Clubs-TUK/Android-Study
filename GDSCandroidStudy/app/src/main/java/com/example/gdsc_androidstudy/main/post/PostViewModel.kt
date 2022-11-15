package com.example.gdsc_androidstudy.main.post

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gdsc_androidstudy.data.CommentData
import com.example.gdsc_androidstudy.data.CommentResponse
import com.example.gdsc_androidstudy.data.PostResponse
import com.example.gdsc_androidstudy.data.User
import com.example.gdsc_androidstudy.network.DBRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {
    val postData = MutableStateFlow<List<PostResponse>?>(null)
    private val dbRepository = DBRepository()
    val comments = MutableStateFlow<List<CommentResponse>?>(null)
    val user = MutableStateFlow<User?>(null)

    init {
        viewModelScope.launch {
            postData.value = kotlin.runCatching {
                dbRepository.getPost()
            }.getOrNull()
        }
    }

    fun getUserProfile(userId: String) {
        viewModelScope.launch {
            launch {
                postData.value = kotlin.runCatching {
                    dbRepository.getPostByUId(userId)
                }.getOrNull()
            }
            launch {
                user.value = kotlin.runCatching {
                    dbRepository.getProfile(userId)
                }.getOrNull()
            }
        }
    }
    fun getComment(postId: Int) {
        viewModelScope.launch {
            comments.value = kotlin.runCatching {
                dbRepository.getComment(postId)
            }.getOrNull()
        }
    }
    fun entryComment(commentData: CommentData) {
        viewModelScope.launch {
            kotlin.runCatching {
                dbRepository.entryComment(commentData)
            }.onSuccess {
                Log.d("hyejin", "success!")
            }.onFailure {
                Log.d("hyejin", it.message.toString())
            }
        }
    }
}
