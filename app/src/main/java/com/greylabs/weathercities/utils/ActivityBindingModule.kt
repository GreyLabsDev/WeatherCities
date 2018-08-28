package com.greylabs.weathercities.utils

import com.greylabs.weathercities.MainActivity
import com.greylabs.weathercities.component.AppScope
import com.greylabs.weathercities.fragments.CityEditorFragment
import com.greylabs.weathercities.fragments.SettingsFragment
import com.greylabs.weathercities.fragments.WeatherFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract fun mainActivity() : MainActivity

    @ContributesAndroidInjector
    abstract fun cityEditorFragment() : CityEditorFragment

    @ContributesAndroidInjector
    abstract fun settingsFragment() : SettingsFragment

    @ContributesAndroidInjector
    abstract fun weatherFragment() : WeatherFragment
}