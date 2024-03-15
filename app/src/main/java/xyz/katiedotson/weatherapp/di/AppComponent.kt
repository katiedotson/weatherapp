package xyz.katiedotson.weatherapp.di

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import xyz.katiedotson.weatherapp.MainActivity
import xyz.katiedotson.weatherapp.WeatherAppApplication
import xyz.katiedotson.weatherapp.home.HomeFragment
import xyz.katiedotson.weatherapp.service.PermissionsService
import xyz.katiedotson.weatherapp.weather.WeatherFragment
import xyz.katiedotson.weatherapp.weather.WeatherUseCase
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        (AndroidInjectionModule::class),
        (AndroidSupportInjectionModule::class),
        (AppModule::class),
        (NetworkModule::class),
    ]
)
interface AppComponent : AndroidInjector<WeatherAppApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    val context: Context
    val application: Application
    val weatherUseCase: WeatherUseCase
    val permissionsService: PermissionsService

    fun inject(mainActivity: MainActivity)
    fun inject(homeFragment: HomeFragment)
    fun inject(weatherFragment: WeatherFragment)

}