package com.tvink28.weather.network

import com.tvink28.weather.data.remote.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApi {

    @GET("onecall?units=metric&exclude=minutely,alerts")
    suspend fun fetchWeatherData(
        @Query("lat") lat: Double?,
        @Query("lon") lon: Double?,
        @Query("appid") apiKey: String
    ): Response<WeatherResponse>
}
