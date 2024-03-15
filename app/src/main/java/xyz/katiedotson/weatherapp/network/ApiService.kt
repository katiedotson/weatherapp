package xyz.katiedotson.weatherapp.network

import retrofit2.http.GET
import retrofit2.http.Query
import xyz.katiedotson.weatherapp.model.WeatherResponse


interface ApiService {

    // https://api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}
    @GET("weather")
    suspend fun getWeather(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String
    ): WeatherResponse

    // https://api.openweathermap.org/data/2.5/weather?lat=39.983334&lon=-82.983330&appid={API key}
    @GET("weather")
    suspend fun getWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String
    ): WeatherResponse
}