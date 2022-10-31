package com.example.toy_proejct.scenarios.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels


class HomeActivity : ComponentActivity() {
    val viewModel by viewModels<HomeViewModel>() //암기

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen(viewModel)
        }
    }
}

