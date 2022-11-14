package kr.ac.tukorea.weather.repository

import io.ktor.client.request.*
import kr.ac.tukorea.weather.data.WeatherData
import kr.ac.tukorea.weather.di.DataModule
import kr.ac.tukorea.weather.di.DataModule.API_KEY
import kr.ac.tukorea.weather.di.DataModule.URL

object Repository {
    suspend fun getWeather(latitude: Double, longitude: Double) : WeatherData {
        return DataModule.client.get(URL + "/data/2.5/weather") {
            parameter("appid", API_KEY)
            parameter("lat", latitude)
            parameter("lon", longitude)
            parameter("lang", "kr")
        }
    }
}