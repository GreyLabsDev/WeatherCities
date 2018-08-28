package com.greylabs.weathercities.component

import android.app.Application
import com.greylabs.weathercities.MainActivity
import com.greylabs.weathercities.fragments.CityEditorFragment
import com.greylabs.weathercities.utils.ActivityBindingModule
import com.greylabs.weathercities.utils.SnacksMachineModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component (modules = [
                AndroidSupportInjectionModule::class,
                SnacksMachineModule::class,
                ActivityBindingModule::class
            ])

interface AppComponent : AndroidInjector<App> {
    override fun inject(instance: App)

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: Application) : AppComponent.Builder
        fun build() : AppComponent
    }

}