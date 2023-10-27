package com.tvink28.weather.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Brush
import com.tvink28.weather.WeatherViewModel
import com.tvink28.weather.ui.theme.CenterColor
import com.tvink28.weather.ui.theme.EndColor
import com.tvink28.weather.ui.theme.StartColor

@Composable
fun HomeScreen(viewModel: WeatherViewModel) {

    val linearGradient = Brush.linearGradient(listOf(StartColor, CenterColor, EndColor))

//    val weatherData by viewModel.weatherData.collectAsState()

    val dialogState = remember {
        mutableStateOf(false)
    }
    if (dialogState.value) {
        DialogSearch(dialogState, viewModel, onSubmit = {
            viewModel.fetchGeo(it)
        })
    }

    Column(
        modifier = androidx.compose.ui.Modifier
            .background(linearGradient)
            .fillMaxSize()
    ) {
        WeatherScreen(viewModel,
            onClickSearch = { dialogState.value = true },
            onClickSync = { viewModel.fetchForecast() }
        )
        TabLayout()
    }
}