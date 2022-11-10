package kr.ac.tukorea.weather.network

sealed class Load<out T : Any> {
    object Loading : Load<Nothing>()
    data class Success<out T : Any>(val data: T) : Load<T>()
    data class Error(val errMsg : String) : Load<Nothing>()
}