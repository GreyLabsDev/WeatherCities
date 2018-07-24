package com.greylabs.weathercities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.greylabs.weathercities.dbtools.DBController
import com.greylabs.weathercities.fragments.FragmentType
import com.greylabs.weathercities.fragments.FragmentsController
import com.greylabs.weathercities.models.CityModel
import com.greylabs.weathercities.models.CityType
import com.greylabs.weathercities.models.SeasonModel
import com.greylabs.weathercities.models.SeasonType
import com.greylabs.weathercities.utils.SnacksMachine.SnacksMachine.initColors
import com.greylabs.weathercities.utils.SnacksMachine.SnacksMachine.setCurrentView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		
		setCurrentView(crdlShacksContainer)
		initColors()
		
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
				}
				R.id.item_settings -> {
					FragmentsController.showFragment(FragmentType.SETTINGS)
				}
				R.id.item_info -> {
					FragmentsController.showFragment(FragmentType.INFO)
				}
			}
			return@setOnNavigationItemSelectedListener true
		}
		
		bnvMainNavigation.menu.findItem(R.id.item_weather).isChecked = true
		
		FragmentsController.setBottomNavigationView(bnvMainNavigation)
		FragmentsController.showFragment(FragmentType.WEATHER)
		
	}
	
	override fun onBackPressed() {
		FragmentsController.showPreviousFragment()
	}
}
