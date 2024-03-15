package xyz.katiedotson.weatherapp.service

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import xyz.katiedotson.weatherapp.model.UserLocationModel
import javax.inject.Inject
import kotlin.coroutines.resume

class PermissionsServiceImpl @Inject constructor(private val context: Context) :
    PermissionsService {

    override val isLocationPermissionAllowed: Boolean
        get() = checkLocationPermission()

    override suspend fun getUserLocation(): UserLocationModel? {
        if (!isLocationPermissionAllowed) return null
        return getUserLocationInternal()
    }

    private fun checkLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    private suspend fun getUserLocationInternal(): UserLocationModel? =
        suspendCancellableCoroutine { continuation ->
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            fusedLocationClient.lastLocation.addOnSuccessListener {
                if (it == null) continuation.resume(null)
                else {
                    continuation.resume(
                        UserLocationModel(
                            lat = it.latitude,
                            lng = it.longitude
                        )
                    )
                }
            }.addOnFailureListener {
                continuation.resume(value = null)
            }
        }
}