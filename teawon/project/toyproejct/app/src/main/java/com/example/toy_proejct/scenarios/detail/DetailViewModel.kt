package com.example.toy_proejct.scenarios.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.toy_proejct.LogHelper
import com.example.toy_proejct.scenarios.detail.data.DetailDto
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class DetailViewModel : ViewModel() {

    private val _detailInfo : MutableState<DetailDto> = mutableStateOf<DetailDto>(
        DetailDto("",
        listOf(),0,"","")
    )
    val detailInfo: MutableState<DetailDto> =_detailInfo; //화면에 표현될 list

    private  val _isLoading: MutableState<Boolean> = mutableStateOf(value = false)
    val isLoading : State<Boolean> = _isLoading

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


    suspend fun getDetailInfo(url:String) {
        _isLoading.value = true
        withContext(Dispatchers.IO) {
            kotlin.runCatching {
                client.get<DetailDto>("http://3.39.75.19:8080/api/v1/crawler/search/product?url=${url}")
            }.onSuccess {
                _detailInfo.value = it //성공시 데이터 갱신
                _isLoading.value = false
                LogHelper.print("success111: ${it}")
            }.onFailure {
                LogHelper.print("faaaailed: $it")
            }
        }
    }

}