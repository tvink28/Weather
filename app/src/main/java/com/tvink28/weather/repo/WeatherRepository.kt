package com.tvink28.weather.repo

import com.tvink28.weather.common.Constants.API_KEY
import com.tvink28.weather.data.remote.GeoResponse
import com.tvink28.weather.data.remote.WeatherResponse
import com.tvink28.weather.network.GeocodingApi
import com.tvink28.weather.network.WeatherApi
import retrofit2.Response
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherApi: WeatherApi,
    private val geocodingApi: GeocodingApi
) {

    suspend fun fetchWeatherData(lat: Double?, lon: Double?): Response<WeatherResponse> {
        return weatherApi.fetchWeatherData(lat, lon, API_KEY)
    }

    suspend fun fetchGeoCode(city: String): Response<GeoResponse> {
        return geocodingApi.fetchGeoCode(city, API_KEY)
    }
}