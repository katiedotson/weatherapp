package xyz.katiedotson.weatherapp.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import xyz.katiedotson.weatherapp.service.PermissionsService
import xyz.katiedotson.weatherapp.service.PermissionsServiceImpl
import javax.inject.Singleton

@Module
open class AppModule {

    @Provides
    fun provideAppContext(application: Application): Context = application.applicationContext

    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    @Provides
    @Singleton
    fun providesPermissionsService(context: Context): PermissionsService {
        return PermissionsServiceImpl(context)
    }

}