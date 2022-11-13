package kr.ac.tukorea.weather.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kr.ac.tukorea.weather.data.WeatherData
import kr.ac.tukorea.weather.network.Load
import kr.ac.tukorea.weather.repository.Repository

class MainViewModel : ViewModel() {

    private val repository by lazy{ Repository() }

    private val _weather = MutableStateFlow<WeatherState>(WeatherState.Loading)
    val weather : StateFlow<WeatherState> = _weather

    fun getCurrentWeather(latitude:Double, longitude:Double){
        viewModelScope.launch{
            when(val loadResult = repository.getWeather(latitude, longitude)){
                is Load.Success -> {
                    _weather.value = WeatherState.Loaded(loadResult.data)
                }
                is Load.Error -> {
                    _weather.value = WeatherState.Error(loadResult.errMsg)
                }
                is Load.Loading -> {
                    _weather.value = WeatherState.Loading
                }
            }
        }
    }
}

sealed class WeatherState {
    object Loading : WeatherState()
    class Loaded (val data: WeatherData) : WeatherState()
    class Error(val message: String) : WeatherState()
}