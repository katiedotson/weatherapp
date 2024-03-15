package xyz.katiedotson.weatherapp.service

import xyz.katiedotson.weatherapp.model.UserLocationModel

interface PermissionsService {
    val isLocationPermissionAllowed: Boolean

    suspend fun getUserLocation(): UserLocationModel?
}