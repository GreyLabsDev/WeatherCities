package com.greylabs.weathercities

import android.os.Bundle
import android.util.Log
import com.greylabs.weathercities.dbtools.DBController
import com.greylabs.weathercities.fragments.FragmentType
import com.greylabs.weathercities.fragments.FragmentsController
import com.greylabs.weathercities.utils.SnacksMachineClass
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var snacks: SnacksMachineClass

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        snacks.initColors()
        snacks.setCurrentView(crdlShacksContainer)

        DBController.destroyDatabase()
        DBController.initDatabase(this)
        FragmentsController.init(this, supportFragmentManager)

        Thread {
            Log.d("DBController", "Cities ${DBController.weatherDataBase?.cityModelDAO()?.getAllCities()?.size}")
            Log.d("DBController", "Seasons ${DBController.weatherDataBase?.seasonModelDAO()?.getAllSeasons()?.size}")
        }.start()

        bnvMainNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_weather -> {
                    FragmentsController.showFragment(FragmentType.WEATHER)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.item_settings -> {
                    FragmentsController.showFragment(FragmentType.SETTINGS)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.item_info -> {
                    FragmentsController.showFragment(FragmentType.INFO)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener true
            }

        }

        bnvMainNavigation.menu.findItem(R.id.item_weather).isChecked = true

        FragmentsController.setBottomNavigationView(bnvMainNavigation)
        FragmentsController.showFragment(FragmentType.WEATHER)

    }

    override fun onBackPressed() {
        FragmentsController.showPreviousFragment()
    }
}
