package com.greylabs.weathercities.di.app

import com.greylabs.weathercities.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        var appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
        return appComponent
    }

    //previous version
//    @Inject
//    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
//
//    override fun onCreate() {
//        super.onCreate()
//        DaggerAppComponent.builder()
//                .application(this)
//                .build()
//                .inject(this)
//    }
//
//    override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingAndroidInjector

    //first version
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