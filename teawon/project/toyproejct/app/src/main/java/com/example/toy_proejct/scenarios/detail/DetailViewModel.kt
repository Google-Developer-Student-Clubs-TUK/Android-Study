package com.example.toy_proejct.scenarios.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.toy_proejct.di.DataModule
import com.example.toy_proejct.domain.product.ProductRepository
import com.example.toy_proejct.data.product.detail.DetailDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class DetailViewModel(private val productRepository: ProductRepository = DataModule.productRepository) : ViewModel() {
    private val _detailInfo: MutableState<DetailDto> = mutableStateOf<DetailDto>(
        DetailDto(
            "",
            listOf(), 0, "", ""
        )
    )
    val detailInfo: MutableState<DetailDto> = _detailInfo; //화면에 표현될 list

    private val _isLoading: MutableState<Boolean> = mutableStateOf(value = false)
    val isLoading: State<Boolean> = _isLoading

    suspend fun getDetailInfo(url: String) {
        withContext(Dispatchers.IO) {
            _isLoading.value = true
            kotlin.runCatching {
                productRepository.fetchProductDetail(url)
            }.onSuccess {
                _detailInfo.value = it //성공시 데이터 갱신
                _isLoading.value = false
            }
        }
    }
}