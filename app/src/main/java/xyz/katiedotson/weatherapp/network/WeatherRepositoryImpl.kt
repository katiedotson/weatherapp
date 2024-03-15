package xyz.katiedotson.weatherapp.network

import xyz.katiedotson.weatherapp.BuildConfig
import xyz.katiedotson.weatherapp.model.WeatherResponse
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    WeatherRepository {
    override suspend fun getWeatherDataForCity(city: String): WeatherResponse? {
        return try {
            apiService.getWeather(city, BuildConfig.API_KEY)
        } catch (e: Throwable) {
            return null
        }
    }

    override suspend fun getWeatherDataForLatLng(
        latitude: Double,
        longitude: Double
    ): WeatherResponse? {
        return try {
            apiService.getWeather(
                latitude = latitude, longitude = longitude,
                apiKey = BuildConfig.API_KEY
            )
        } catch (e: Throwable) {
            return null
        }
    }
}