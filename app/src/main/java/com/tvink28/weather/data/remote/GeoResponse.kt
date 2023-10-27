package com.tvink28.weather.data.remote

class GeoResponse : ArrayList<GeoResponseItem>()

data class GeoResponseItem(
    val country: String,
    val lat: Double,
    val local_names: LocalNames,
    val lon: Double,
    val name: String,
    val state: String
)

data class LocalNames(
    val en: String
)