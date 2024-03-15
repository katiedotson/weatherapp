package xyz.katiedotson.weatherapp.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResponse(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Long,
    val sys: Sys,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int
)

@JsonClass(generateAdapter = true)
data class Coord(
    val lon: Double,
    val lat: Double
)

@JsonClass(generateAdapter = true)
data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

@JsonClass(generateAdapter = true)
data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int
)

@JsonClass(generateAdapter = true)
data class Wind(
    val speed: Double,
    val deg: Int
)

@JsonClass(generateAdapter = true)
data class Clouds(
    val all: Int
)

@JsonClass(generateAdapter = true)
data class Sys(
    val type: Int,
    val id: Int,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)
