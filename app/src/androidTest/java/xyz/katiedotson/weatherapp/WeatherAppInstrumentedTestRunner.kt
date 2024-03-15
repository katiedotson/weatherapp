package xyz.katiedotson.weatherapp

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class WeatherAppInstrumentedTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, TestWeatherAppApplication::class.java.name, context)
    }
}