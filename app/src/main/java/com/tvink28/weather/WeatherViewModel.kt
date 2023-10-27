package com.tvink28.weather

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tvink28.weather.data.remote.GeoResponseItem
import com.tvink28.weather.data.remote.LocalNames
import com.tvink28.weather.data.remote.WeatherResponse
import com.tvink28.weather.repo.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    val defaultGeoList = List(3) {
        GeoResponseItem("", 0.0, LocalNames(""), 0.0, "", "")
    }

    private val _weatherData = MutableStateFlow<WeatherResponse?>(null)
    val weatherData: StateFlow<WeatherResponse?> = _weatherData

    private val _geoData = MutableStateFlow<List<GeoResponseItem>?>(null)
    val geoData: StateFlow<List<GeoResponseItem>?> = _geoData

    val selectedCity = MutableStateFlow<GeoResponseItem?>(null)

    fun fetchGeo(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _geoData.value = weatherRepository.fetchGeoCode(city).body()

            } catch (e: Exception) {
                Log.e("LOGG", "Error fetchGeo: ${e.message}")
            }
        }
    }

    fun fetchForecast() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val lat = selectedCity.value?.lat
                val lon = selectedCity.value?.lon
                val response = weatherRepository.fetchWeatherData(lat, lon)
                _weatherData.value = response.body()
            } catch (e: Exception) {
                Log.e("LOGG", "Error fetchForecast: ${e.message}")
            }
        }

    }
}