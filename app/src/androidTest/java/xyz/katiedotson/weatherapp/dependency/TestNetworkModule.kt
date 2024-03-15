package xyz.katiedotson.weatherapp.dependency

import dagger.Module
import dagger.Provides
import xyz.katiedotson.weatherapp.dependency.FakeApiService
import xyz.katiedotson.weatherapp.network.WeatherRepository
import xyz.katiedotson.weatherapp.network.WeatherRepositoryImpl

@Module
object TestNetworkModule {

    @Provides
    fun providesWeatherRepository(): WeatherRepository {
        return WeatherRepositoryImpl(FakeApiService())
    }

}
