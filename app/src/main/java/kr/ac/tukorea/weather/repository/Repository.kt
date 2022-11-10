package kr.ac.tukorea.weather.repository

import kr.ac.tukorea.weather.data.WeatherData
import kr.ac.tukorea.weather.network.ExceptControl
import kr.ac.tukorea.weather.network.Load
import kr.ac.tukorea.weather.network.WeatherAPI

class Repository {
    suspend fun getWeather(latitude: Double, longitude: Double) : Load<WeatherData> {
        return ExceptControl.ExceptionCall{
            WeatherAPI.getWeather(latitude, longitude)
        }
    }
}