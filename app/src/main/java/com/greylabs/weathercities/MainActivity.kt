package com.greylabs.weathercities

import android.app.Application
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.greylabs.weathercities.component.App
import com.greylabs.weathercities.component.AppComponent
import com.greylabs.weathercities.dbtools.DBController
import com.greylabs.weathercities.fragments.FragmentType
import com.greylabs.weathercities.fragments.FragmentsController
import com.greylabs.weathercities.models.CityModel
import com.greylabs.weathercities.models.CityType
import com.greylabs.weathercities.models.SeasonModel
import com.greylabs.weathercities.models.SeasonType
import com.greylabs.weathercities.utils.SnacksMachine
import com.greylabs.weathercities.utils.SnacksMachineClass
import com.greylabs.weathercities.utils.SnacksMachineModule
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

	@Inject
	lateinit var snacks : SnacksMachineClass
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		App.getComponent().inject(this)


		SnacksMachine.SnacksMachine.setCurrentView(crdlShacksContainer)
		SnacksMachine.SnacksMachine.initColors()

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
