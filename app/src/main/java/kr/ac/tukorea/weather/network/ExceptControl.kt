package kr.ac.tukorea.weather.network

import kr.ac.tukorea.weather.data.WeatherData

object ExceptControl {
    private const val DEFAULT_RETRY_COUNT = 3

    suspend fun<T:Any> ExceptionCall(
        call: suspend() -> T
    ): Load<T>{
        var cnt = 0
        while (cnt < DEFAULT_RETRY_COUNT){
            try{
                val response = call.invoke()
                cnt = DEFAULT_RETRY_COUNT + 1

                if(response is WeatherData){
                    if (response.cod!! == 200){
                        return if(response != null){
                            Load.Success(response)
                        } else{
                            Load.Loading
                        }
                    }
                }
            } catch (e: Exception){
                Load.Error("날씨 로딩에 실패했습니다.")
            }
        }
        return Load.Error("오류가 발생했습니다.")
    }
}