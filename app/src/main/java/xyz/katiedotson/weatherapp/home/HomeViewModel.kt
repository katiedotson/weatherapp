package xyz.katiedotson.weatherapp.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import xyz.katiedotson.weatherapp.WeatherAppApplication
import xyz.katiedotson.weatherapp.service.PermissionsService
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val permissionsService: PermissionsService
): ViewModel() {

    private val _events = MutableStateFlow(emptyList<Event>())
    val events = _events.asStateFlow()

    // skip ahead to weather screen if the user already allowed permissions
    init {
        if (permissionsService.isLocationPermissionAllowed) {
            _events.update {
                it + Event.LocationPermissionAllowed
            }
        }
    }

    fun handlePermissionResult(wasGranted: Boolean) {
        val event = if (wasGranted) Event.LocationPermissionAllowed else Event.LocationPermissionDenied
        _events.update {
            it + event
        }
    }

    fun markEventHandled(event: Event) {
        _events.update {
            it - event
        }
    }

    sealed class Event {
        object LocationPermissionAllowed : Event()
        object LocationPermissionDenied: Event()
    }

    // I don't like using Dagger this way, but given that I'm used to Hilt
    // this is where I landed
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val permissionsService = (this[APPLICATION_KEY] as WeatherAppApplication).applicationInjector().permissionsService
                HomeViewModel(
                    permissionsService = permissionsService
                )
            }
        }
    }

}