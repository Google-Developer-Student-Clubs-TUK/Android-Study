package kr.ac.tukorea.weather.network

//object WeatherAPI {
//    const val API_KEY = "9a24b96b3ff42648de6d674032a49494"
//    const val URL = "https://api.openweathermap.org"
//
//    val client: HttpClient = HttpClient() {
//        expectSuccess = true
//
//        install(HttpTimeout) {
//            requestTimeoutMillis = 30000L
//            connectTimeoutMillis = 30000L
//            socketTimeoutMillis = 30000L
//        }
//
//        install(HttpRequestRetry) {
////            maxRetries = 5
//            retryOnExceptionIf() { request, cause ->
//                cause is CancellationException
//            }
//        }
//
//        install(ContentNegotiation) {
//            var converter = KotlinxSerializationConverter(Json {
//                prettyPrint = true
//                ignoreUnknownKeys = true
//                explicitNulls = false
//            })
//
//            register(ContentType.Application.Json, converter)
//        }
//    }
//
//    suspend fun getWeather(latitude: Double, longitude: Double): WeatherData {
//        return try {
//            val httpResponse = client.get(URL + "/data/2.5/weather") {
//                parameter("appid", API_KEY)
//                parameter("lat", latitude)
//                parameter("lon", longitude)
//            }.body<WeatherData>()
//            httpResponse
//        } catch (e: Exception) {
//            Log.d("error", "에러났쇼")
//            WeatherData()
//        }
//    }
//}