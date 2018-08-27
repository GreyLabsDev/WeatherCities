package com.greylabs.weathercities.utils

import android.support.annotation.NonNull
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class SnacksMachineModule {

    //First version of module

//    @Provides
//    @NonNull
//    @Singleton
//    fun provideSnacksMachine() : SnacksMachineClass = SnacksMachineClass()


    //Binds-version of module

    @Binds
    @NonNull
    @Singleton
    abstract fun provideSnacksMachine() : SnacksMachineClass
}