package xyz.katiedotson.weatherapp.dependency

import xyz.katiedotson.weatherapp.model.Clouds
import xyz.katiedotson.weatherapp.model.Coord
import xyz.katiedotson.weatherapp.model.Main
import xyz.katiedotson.weatherapp.model.Sys
import xyz.katiedotson.weatherapp.model.Weather
import xyz.katiedotson.weatherapp.model.WeatherResponse
import xyz.katiedotson.weatherapp.model.Wind
import xyz.katiedotson.weatherapp.network.ApiService

class FakeApiService: ApiService {
    override suspend fun getWeather(cityName: String, apiKey: String): WeatherResponse {
        return fakeWeather
    }


    override suspend fun getWeather(
        latitude: Double,
        longitude: Double,
        apiKey: String
    ): WeatherResponse {
        return fakeWeather.copy(name = "lat lng search")
    }

    private val fakeWeather = WeatherResponse(
        coord = Coord(0.0, 0.0),
        weather = listOf(Weather(
            id = 1,
            main = "main text",
            description = "Wintery mix",
            icon = "",
        )),
        base = "",
        main = Main(
            temp = 0.0,
            feels_like = 0.0,
            temp_min = 0.0,
            temp_max = 0.0,
            pressure = 1,
            humidity = 1
        ),
        visibility = 1,
        wind = Wind(
            speed = 0.0,
            deg = 1,
        ),
        clouds = Clouds(
            all = 0,
        ),
        dt = 1L,
        sys = Sys(
            type = 1,
            id = 1,
            country = "",
            sunrise = 1L,
            sunset = 1L
        ),
        timezone = 1,
        id = 1,
        name = "city name search",
        cod = 1
    )
}