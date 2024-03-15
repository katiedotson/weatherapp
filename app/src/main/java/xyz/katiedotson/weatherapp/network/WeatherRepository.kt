package xyz.katiedotson.weatherapp.network

import xyz.katiedotson.weatherapp.model.WeatherResponse

interface WeatherRepository {
    // usually I'd want to see these mapped to different models
    // either in the repo or a use case to prevent
    // leakage of types through different classes
    // additonally these are just nulls in the error scenario
    suspend fun getWeatherDataForCity(city: String): WeatherResponse?
    suspend fun getWeatherDataForLatLng(latitude: Double, longitude: Double): WeatherResponse?
}