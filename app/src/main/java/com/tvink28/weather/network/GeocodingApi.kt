package com.tvink28.weather.network

import com.tvink28.weather.data.remote.GeoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApi {

    @GET("direct")
    suspend fun fetchGeoCode(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String,
        @Query("limit") limit: Int = 3
    ): Response<GeoResponse>
}