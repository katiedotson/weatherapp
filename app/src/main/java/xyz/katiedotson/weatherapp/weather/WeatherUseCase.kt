package xyz.katiedotson.weatherapp.weather

import android.content.SharedPreferences
import xyz.katiedotson.weatherapp.model.WeatherResponse
import xyz.katiedotson.weatherapp.network.WeatherRepository
import xyz.katiedotson.weatherapp.service.PermissionsService
import javax.inject.Inject

class WeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val permissionsService: PermissionsService,
    private val sharedPreferences: SharedPreferences // ideally would be wrapped
) {

    private val previousSearchLat get() = sharedPreferences.getString(PreviousSearchLatKey, null)
    private val previousSearchLng get() = sharedPreferences.getString(PreviousSearchLngKey, null)

    suspend operator fun invoke(): WeatherResponse? {
        val userLoc = permissionsService.getUserLocation()
        return if (userLoc != null) {
            sharedPreferences.edit().putString(PreviousSearchLatKey, userLoc.lat.toString()).apply()
            sharedPreferences.edit().putString(PreviousSearchLngKey, userLoc.lng.toString()).apply()
            weatherRepository.getWeatherDataForLatLng(
                latitude = userLoc.lat,
                longitude = userLoc.lng
            )
        } else if (previousSearchLat != null && previousSearchLng != null) {
            weatherRepository.getWeatherDataForLatLng(
                latitude = previousSearchLat!!.toDouble(),
                longitude = previousSearchLng!!.toDouble()
            )
        } else {
            // original idea here was to have the user type a city
            weatherRepository.getWeatherDataForCity(city = "Columbus")
        }
    }

    companion object {
        private const val PreviousSearchLatKey = "PREVIOUS_SEARCH_LAT"
        private const val PreviousSearchLngKey = "PREVIOUS_SEARCH_LNG"
    }
}