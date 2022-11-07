package com.example.toy_proejct.scenarios.home

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.toy_proejct.scenarios.home.data.Item
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {






    private val _searchWidgetState: MutableState<Boolean> = //검색창 활성화 여부
        mutableStateOf(value = false)
    val searchWidgetState: State<Boolean> = _searchWidgetState

    private val _searchTextState: MutableState<String> = //검색 텍스트 값
        mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    private val _itemList : MutableState<List<Item>> = mutableStateOf(listOf());
    val itemList:State<List<Item>> =_itemList; //화면에 표현될 list

    fun updateSearchWidgetState(newState: Boolean) { //활성화 여부 변경 함수
        _searchWidgetState.value = newState
    }

    fun updateSearchTextState(newValue: String) { //검색 텍스트 값 변경 함수
        _searchTextState.value = newValue
    }






    private val client: HttpClient = HttpClient(CIO)

    suspend fun searchApi(keyword:String): String =
        withContext(Dispatchers.IO) {
            val url = "http://3.39.75.19:8080/api/v1/crawler/search/products?word=%EB%A7%A5%EB%B6%81"
            val response: HttpResponse = client.get(url)
            val responseStatus = response.status
            Log.d(TAG, "requestKtorIo: $responseStatus")
            //값이 정상적으로 받아진다면 json데이터를 파싱하여 itemList값에 넣기
            if (responseStatus == HttpStatusCode.OK) {
                response.readText()
            } else {
                "error: $responseStatus"
            }
        }


    //API구현
}


