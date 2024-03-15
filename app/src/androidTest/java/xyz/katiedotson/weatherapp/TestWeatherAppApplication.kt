package xyz.katiedotson.weatherapp

import xyz.katiedotson.weatherapp.dependency.DaggerTestAppComponent

class TestWeatherAppApplication: WeatherAppApplication() {
    override val applicationInjector = DaggerTestAppComponent.builder()
        .application(this)
        .build()

    override fun applicationInjector() = applicationInjector

    override fun onCreate() {
        super.onCreate()
        applicationInjector.inject(this)
    }
}