package xyz.katiedotson.weatherapp

import androidx.fragment.app.Fragment
import dagger.android.DaggerApplication
import xyz.katiedotson.weatherapp.di.AppComponent
import xyz.katiedotson.weatherapp.di.DaggerAppComponent

open class WeatherAppApplication : DaggerApplication() {

    open val applicationInjector = DaggerAppComponent.builder()
        .application(this)
        .build()

    public override fun applicationInjector() = applicationInjector

    override fun onCreate() {
        super.onCreate()
        applicationInjector.inject(this)
    }

}

val Fragment.appComponent: AppComponent get() = (requireActivity().application as WeatherAppApplication).applicationInjector()
