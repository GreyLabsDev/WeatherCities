package com.greylabs.weathercities.component

import android.app.Application
import com.greylabs.weathercities.utils.SnacksMachineModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        component = buildComponent()
    }

    companion object {
        private lateinit var component : AppComponent

        fun getComponent() : AppComponent = component

        fun buildComponent() : AppComponent = DaggerAppComponent.builder()
                .snacksMachineModule(SnacksMachineModule())
                .build()
    }
}