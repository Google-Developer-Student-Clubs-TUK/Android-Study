package kr.ac.tukorea.weather.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import kotlinx.serialization.json.Json
import kr.ac.tukorea.weather.data.WeatherData

object WeatherAPI {
    const val API_KEY = "9a24b96b3ff42648de6d674032a49494"
    const val URL = "https://api.openweathermap.org"

    val client: HttpClient = HttpClient() {
        expectSuccess = true

        install(HttpTimeout) {
            requestTimeoutMillis = 30000L
            connectTimeoutMillis = 30000L
            socketTimeoutMillis = 30000L
        }

        install(ContentNegotiation) {
            var converter = KotlinxSerializationConverter(Json {
                prettyPrint = true
                ignoreUnknownKeys = true
                explicitNulls = false
            })

            register(ContentType.Application.Json, converter)
        }
    }

    suspend fun getWeather(latitude: Double, longitude: Double): WeatherData {
        return try {
            val httpResponse = client.get(URL + "/data/2.5/weather") {
                parameter("appid", API_KEY)
                parameter("lat", latitude)
                parameter("lon", longitude)
            }.body<WeatherData>()
            httpResponse
        } catch (e: Exception) {
            WeatherData()
        }
    }
}