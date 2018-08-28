package com.greylabs.weathercities.di.module

import com.greylabs.weathercities.MainActivity
import com.greylabs.weathercities.fragments.CityEditorFragment
import com.greylabs.weathercities.fragments.InfoFragment
import com.greylabs.weathercities.fragments.SettingsFragment
import com.greylabs.weathercities.fragments.WeatherFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

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

    @ContributesAndroidInjector
    abstract fun infoFragment() : InfoFragment
}