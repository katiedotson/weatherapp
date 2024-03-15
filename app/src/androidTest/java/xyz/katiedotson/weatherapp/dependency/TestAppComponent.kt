package xyz.katiedotson.weatherapp.dependency

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import xyz.katiedotson.weatherapp.HomeScreenTests
import xyz.katiedotson.weatherapp.di.AppComponent
import xyz.katiedotson.weatherapp.di.AppModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        AppModule::class,
        TestNetworkModule::class
    ]
)
interface TestAppComponent : AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): TestAppComponent
    }

    fun inject(homeScreenTests: HomeScreenTests)
}
