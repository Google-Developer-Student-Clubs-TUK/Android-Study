package com.example.gdsc_androidstudy.main.post

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class PostViewModel : ViewModel() {
    val postData = MutableStateFlow<List<PostData>>(listOf(PostData("zinkikixx", "", "오늘의 OOTT", listOf(""), ""), PostData("zinkikixx", "", "오늘의 OOTT", listOf(""), "")))

    fun getPostData(){

    }
}