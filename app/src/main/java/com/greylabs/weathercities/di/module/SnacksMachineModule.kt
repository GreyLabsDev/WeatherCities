package com.greylabs.weathercities.di.module

import android.support.annotation.NonNull
import com.greylabs.weathercities.utils.SnacksMachineClass
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SnacksMachineModule {

    //First version of module

    @Provides
    @NonNull
    @Singleton
    fun provideSnacksMachine() : SnacksMachineClass = SnacksMachineClass()

    //Binds-version of module

//    @Binds
//    @NonNull
//    @Singleton
//    abstract fun provideSnacksMachine() : SnacksMachineClass

}