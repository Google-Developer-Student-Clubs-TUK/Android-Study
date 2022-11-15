package kr.ac.tukorea.weather.scenarios

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kr.ac.tukorea.weather.R
import kr.ac.tukorea.weather.ui.theme.sky
import kr.ac.tukorea.weather.ui.theme.skyblue
import kr.ac.tukorea.weather.utils.getWeatherImage
import kr.ac.tukorea.weather.viewModel.MainViewModel
import kr.ac.tukorea.weather.viewModel.WeatherState

@Composable
fun Home(viewModel: MainViewModel, navController: NavHostController) {
    Scaffold(
    ) {
        Column(
            Modifier
                .fillMaxSize()
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
                    ShowWeather(state)
                }
            }

        }

    }
}

@Composable
private fun ShowWeather(state: WeatherState.Loaded) {
    val name = state.data.name ?: "-"
    val description = state.data.weather!![0]!!.description!!.substring(0, 1)
        .uppercase() + state.data.weather[0]!!.description!!.substring(
        1
    ).lowercase()
    val temp = "${((state.data.main!!.temp!! - 273.15)).toInt()}°"
    val feelTemp = "체감 기온 ${((state.data.main.feels_like!! - 273.15)).toInt()}°"
    val locIcon = Icons.Default.LocationOn
    val humidipy = "습도 ${((state.data.main.humidity))}%"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.sky),
                contentScale = ContentScale.FillHeight
            )
    ) {
        Text(
            text = "오늘의 날씨",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 70.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = locIcon, contentDescription = "위치", tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
            Text(
                text = name,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .padding(top = 0.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(
                        id = getWeatherImage(state.data.weather[0]!!.icon!!)
                    ),
                    contentDescription = "",
                    modifier = Modifier.size(200.dp)
                        .padding(top = 30.dp)
                )
                Text(
                    text = temp,
                    modifier = Modifier
                        .padding(start = 10.dp)
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
                    fontSize = 45.sp,
                    fontWeight = FontWeight.W700,
                    lineHeight = 60.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = description,
                    color = sky,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(top = 50.dp)
                )
                Text(
                    modifier = Modifier
                        .padding(top = 15.dp),
                    text = feelTemp,
                    color = sky,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End,
                )
                Text(
                    modifier = Modifier
                        .padding(top = 15.dp),
                    text = humidipy,
                    color = sky,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End,
                )
            }
        }
    }
}