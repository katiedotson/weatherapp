package xyz.katiedotson.weatherapp.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch
import xyz.katiedotson.weatherapp.WeatherAppApplication
import xyz.katiedotson.weatherapp.model.Weather
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    private val weatherUseCase: WeatherUseCase
) : ViewModel() {

    private val _weatherState: MutableLiveData<WeatherState> = MutableLiveData()
    val weatherState: LiveData<WeatherState> = _weatherState

    init {
        viewModelScope.launch {
            try {
                _weatherState.value = WeatherState.Loading
                val weather = weatherUseCase()
                // ideally the use case would have told us if something went wrong here
                weather!!.weather.first().let {
                    _weatherState.value = WeatherState.Success(
                        weather = it,
                        city = weather.name
                    )
                }
            } catch (e: Throwable) {
                // should log something here, and probably handle different error cases
                // to communicate to the user what happened, ie. no network connection, API failure, etc.
                _weatherState.value = WeatherState.Error
            }
        }
    }

    sealed class WeatherState {
        object Loading : WeatherState()

        // wouldn't generally send a response type all the way back to the view
        // would map this to something more view-specific
        data class Success(val weather: Weather, val city: String) : WeatherState()
        object Error : WeatherState()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val appInjector = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as WeatherAppApplication).applicationInjector()
                WeatherViewModel(
                    appInjector.weatherUseCase
                )
            }
        }
    }
}