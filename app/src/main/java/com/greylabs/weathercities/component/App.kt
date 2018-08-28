package com.greylabs.weathercities.component

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

class App : Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>



    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingAndroidInjector

//    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
//        var appComponent = DaggerAppComponent.builder().application(this).build()
//        appComponent.inject(this)
//        return  appComponent
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//        component = buildComponent()
//    }
//
//    companion object {
//        private lateinit var component : AppComponent
//
//        fun getComponent() : AppComponent = component
//
//        fun buildComponent() : AppComponent = DaggerAppComponent.builder()
//                .snacksMachineModule(SnacksMachineModule())
//                .build()
//    }


}