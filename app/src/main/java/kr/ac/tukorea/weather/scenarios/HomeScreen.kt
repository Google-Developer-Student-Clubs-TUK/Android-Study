package kr.ac.tukorea.weather.scenarios

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kr.ac.tukorea.weather.R
import kr.ac.tukorea.weather.ui.theme.skyblue
import kr.ac.tukorea.weather.ui.theme.skybluedark
import kr.ac.tukorea.weather.utils.getWeatherImage
import kr.ac.tukorea.weather.viewModel.MainViewModel
import kr.ac.tukorea.weather.viewModel.WeatherState
import java.util.*

@Composable
fun Home(viewModel: MainViewModel, navController: NavHostController) {
    Scaffold(
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(top = 13.dp)
        ) {
            when (val state = viewModel.weather.collectAsState().value) {
                is WeatherState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is WeatherState.Error -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = state.message)
                    }
                }
                is WeatherState.Loaded -> {
                    showToday(state)
                    showWeather(state)
                }
            }

        }

    }
}

@Composable
fun showToday(state: WeatherState.Loaded) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp)
    ) {
        Text(
            text = state.data.name!!,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = state.data.name!!,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Composable
fun showWeather(state: WeatherState.Loaded) {
    val width = LocalConfiguration.current.screenWidthDp
    val height = LocalConfiguration.current.screenHeightDp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Card(
                elevation = 20.dp,
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
                    .height((height * 0.25).dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    skyblue, skybluedark
                                )
                            )
                        )
                        .padding(top = 0.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
                        .fillMaxSize()
                        ) {
                            Text(
                                state.data.weather!![0]!!.description!!.substring(0, 1)
                                    .toUpperCase() + state.data.weather!![0]!!.description!!.substring(
                                    1
                                ).toLowerCase(),
                                color = Color.White,
                                fontSize = 25.sp,
                                fontWeight = FontWeight.W800,
                                fontFamily = FontFamily.Serif,
                                modifier = Modifier
                                    .align(Alignment.Bottom)
                                    .weight(6f)
                            )
                    Column(
                        Modifier.weight(5f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(
                            text = "${((state.data.main!!.temp!! - 273.15)).toInt()}°",
                            modifier = Modifier
                                .graphicsLayer(alpha = 0.99f)
                                .drawWithCache {
                                    val brush = Brush.verticalGradient(
                                        listOf(
                                            Color.White,
                                            skyblue
                                        )
                                    )
                                    onDrawWithContent {
                                        drawContent()
                                        drawRect(
                                            brush,
                                            blendMode = BlendMode.SrcAtop
                                        )
                                    }
                                },
                            fontSize = 70.sp,
                            fontWeight = FontWeight.W700,
                            lineHeight = 60.sp,
                        )
                        Text(
                            modifier = Modifier,
                            text = "체감 기온 ${((state.data.main!!.feels_like!! - 273.15)).toInt()}°",
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.End,
                        )
                    }
                    Image(
                        painter = painterResource(
                            id = getWeatherImage(state.data.weather!![0]!!.icon!!)
                        ),
                        contentDescription = "",
                        modifier = Modifier
                            .height((height * 0.17).dp)
                            .width((width * 0.5).dp),
                        alignment = Alignment.TopCenter
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column() {

                        Card(
                            elevation = 0.dp,
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .padding(top = 35.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.i04d),
                                contentDescription = "",
                                modifier = Modifier
                                    .height((width * 0.18).dp)
                                    .width((width * 0.18).dp)
                                    .background(
                                        Color.Blue.copy(alpha = 0.05f)
                                    )
                                    .padding(15.dp),
                                alignment = Alignment.TopCenter
                            )
                        }

                        Text(
                            modifier = Modifier
                                .width((width * 0.18).dp)
                                .padding(vertical = 10.dp),
                            text = state.data.cloud?.all.toString() + "%",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                    Column() {

                        Card(
                            elevation = 0.dp,
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .padding(top = 35.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.wind),
                                contentDescription = "",
                                modifier = Modifier
                                    .height((width * 0.18).dp)
                                    .width((width * 0.18).dp)
                                    .background(
                                        Color.Blue.copy(alpha = 0.05f)
                                    )
                                    .padding(15.dp),
                                alignment = Alignment.TopCenter,
                                colorFilter = ColorFilter.tint(Color.Black)
                            )
                        }

                        Text(
                            modifier = Modifier
                                .width((width * 0.18).dp)
                                .padding(vertical = 10.dp),
                            text = (state.data.wind?.speed!! * 3600 / 1000).toInt()
                                .toString() + " km/h",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                    Column() {

                        Card(
                            elevation = 0.dp,
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .padding(top = 35.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.humidity),
                                contentDescription = "",
                                modifier = Modifier
                                    .height((width * 0.18).dp)
                                    .width((width * 0.18).dp)
                                    .background(
                                        Color.Blue.copy(alpha = 0.05f)
                                    )
                                    .padding(10.dp),
                                alignment = Alignment.TopCenter
                            )
                        }

                        Text(
                            modifier = Modifier
                                .width((width * 0.18).dp)
                                .padding(vertical = 10.dp),
                            text = state.data.main?.humidity.toString() + "%",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}