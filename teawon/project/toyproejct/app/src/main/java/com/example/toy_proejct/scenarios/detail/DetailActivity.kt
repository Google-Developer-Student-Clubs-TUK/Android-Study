package com.example.toy_proejct.scenarios.detail

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.toy_proejct.scenarios.home.HomeScreen
import com.example.toy_proejct.scenarios.home.HomeViewModel


class DetailActivity : ComponentActivity() {

    val viewModel by viewModels<DetailViewModel>() //암기


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val title = intent.getStringExtra("title")
        val minimumPrice = intent.getIntExtra("minimumPrice", -1)
        setContent {
            DetailScreen(viewModel, title, minimumPrice) { finishActivity() }
        }
    }

    private fun finishActivity() {
        finish()
    }
}