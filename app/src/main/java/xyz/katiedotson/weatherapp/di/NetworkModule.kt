package xyz.katiedotson.weatherapp.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import xyz.katiedotson.weatherapp.network.ApiService
import xyz.katiedotson.weatherapp.network.WeatherRepository
import xyz.katiedotson.weatherapp.network.WeatherRepositoryImpl
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            .build()
    }

    @Provides
    @Singleton
    fun providesMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    @Singleton
    fun provideApiService(moshi: Moshi, okHttpClient: OkHttpClient): ApiService {
        val retrofit: Retrofit = Retrofit.Builder()
            // TODO: belongs in properties file
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(apiService: ApiService): WeatherRepository {
        return WeatherRepositoryImpl(apiService)
    }

}