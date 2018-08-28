package com.greylabs.weathercities.di.component

import android.app.Application
import com.greylabs.weathercities.di.app.App
import com.greylabs.weathercities.di.module.ActivityBindingModule
import com.greylabs.weathercities.di.module.SnacksMachineModule
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
        fun application(application: Application) : Builder
        fun build() : AppComponent
    }

}