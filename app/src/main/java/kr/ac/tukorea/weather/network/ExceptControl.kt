package kr.ac.tukorea.weather.network

import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kr.ac.tukorea.weather.data.WeatherData
import java.io.IOException
import java.net.SocketTimeoutException

object ExceptControl {
    private const val DEFAULT_RETRY_COUNT = 3

    suspend fun<T: Any> exceptionCall(
        needRetry: Boolean = true,
        call: suspend () -> T
    ): Load<T> {
        var count = 0
        var backOffTime = 2_000L
        while (count < DEFAULT_RETRY_COUNT) {
            try {
                val response = call.invoke()
                count = DEFAULT_RETRY_COUNT + 1

                if (response is WeatherData){
                    if (response.cod!! == 200) {
                        return if (response != null) {
                            Load.Success(response)
                        } else {
                            Load.Loading
                        }
                    }
                }

            } catch (socketTimeOutException: SocketTimeoutException) {
                if (socketTimeOutException.message != null) {
                    return Load.Error("Parse error : ${socketTimeOutException.message}")
                } else {
                    return Load.Error("")
                }
            } catch (ioException: IOException) {
                ioException.printStackTrace()
                if (needRetry) {
                    delay(backOffTime)
                    count++
                    backOffTime *= 2
                } else {
                    if (ioException.message != null) {
                        return Load.Error("")
                    } else {
                        return Load.Error("")
                    }
                }
            } catch (cancellationException: CancellationException) {
                cancellationException.printStackTrace()
                if (cancellationException.message != null) {
                    return Load.Error(
                        "Parse error : ${cancellationException.message}",
                    )
                } else {
                    return Load.Error("ERROR_CODE_CANCELLATION_JOB")
                }

            } catch (syntaxException: JsonSyntaxException) {
                if (syntaxException.message != null) {
                    return Load.Error("${syntaxException.message}")
                } else {
                    return Load.Error("Something went wrong")
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
                if (exception.message != null) {
                    return Load.Error("Parse error : ${exception.message}")
                } else {
                    // An exception was thrown when calling the API so we're converting this to an ErrorBody
                    return Load.Error("SomeThing went wrong")
                }
            }
        }
        return Load.Error("오류가 발생했습니다.")
    }
}