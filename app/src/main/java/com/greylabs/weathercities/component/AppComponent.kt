package com.greylabs.weathercities.component

import com.greylabs.weathercities.MainActivity
import com.greylabs.weathercities.fragments.CityEditorFragment
import com.greylabs.weathercities.utils.SnacksMachineModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component (modules = [
                SnacksMachineModule::class
            ])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(cityEditorFragment: CityEditorFragment)
}