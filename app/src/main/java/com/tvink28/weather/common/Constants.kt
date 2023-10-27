package com.tvink28.weather.common

import com.tvink28.weather.BuildConfig

object Constants {
    const val API_KEY = BuildConfig.API_KEY
    const val WEATHER_FORECAST_END_POINT = "https://api.openweathermap.org/data/3.0/"
    const val GEOCODING_END_POINT = "http://api.openweathermap.org/geo/1.0/"
}