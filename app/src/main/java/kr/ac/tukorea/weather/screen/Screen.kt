package kr.ac.tukorea.weather.screen

sealed class Screen(val route: String){
    object Home: Screen("Home")
}
