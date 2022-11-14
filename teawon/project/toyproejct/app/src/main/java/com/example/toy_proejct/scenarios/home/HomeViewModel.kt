package com.example.toy_proejct.scenarios.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.toy_proejct.LogHelper
import com.example.toy_proejct.scenarios.home.data.GetSearchList
import com.example.toy_proejct.scenarios.home.data.ProductListDto
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {





    private  val _isLoading: MutableState<Boolean> = mutableStateOf(value = false)
    val isLoading : State<Boolean> = _isLoading


    private val _searchWidgetState: MutableState<Boolean> = //검색창 활성화 여부
        mutableStateOf(value = false)
    val searchWidgetState: State<Boolean> = _searchWidgetState

    private val _searchTextState: MutableState<String> = //검색 텍스트 값
        mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    private val _itemList : MutableState<List<ProductListDto>> = mutableStateOf(listOf())
    val itemList:State<List<ProductListDto>> =_itemList; //화면에 표현될 list

    fun updateSearchWidgetState(newState: Boolean) { //활성화 여부 변경 함수
        _searchWidgetState.value = newState
    }

    fun updateSearchTextState(newValue: String) { //검색 텍스트 값 변경 함수
        _searchTextState.value = newValue
    }






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


    suspend fun searchApi(keyword:String) {
        _isLoading.value = true
        withContext(Dispatchers.IO) {
             kotlin.runCatching {
                client.get<GetSearchList>("http://3.39.75.19:8080/api/v1/crawler/search/products?word=$keyword")
            }.onSuccess {
                 _isLoading.value = false
                _itemList.value = it.productListDtoList //성공시 데이터 갱신
                LogHelper.print("succses: ${it.productListDtoList.size}")
            }.onFailure {
                LogHelper.print("Failure: $it")
             }
        }
    }


}


