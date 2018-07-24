package com.greylabs.weathercities.dbtools

import android.content.Context
import android.util.Log
import com.greylabs.weathercities.models.CityModel
import com.greylabs.weathercities.models.CityType
import com.greylabs.weathercities.models.SeasonModel
import com.greylabs.weathercities.models.SeasonType

internal class DBController {

	companion object {
		private val TAG = "DBController"
		var weatherDataBase: WeatherDataBase? = null
		
		fun eraseLinkedSeasonsForCity(cityName: String) {
			DBController.weatherDataBase?.seasonModelDAO()?.getSeasonsForCity(cityName)?.let {
				if (it.isNotEmpty()) {
					it.forEach {
						DBController.weatherDataBase?.seasonModelDAO()?.delete(it)
					}
				}
			}
		}

		fun initDatabase(context: Context) {
			weatherDataBase = WeatherDataBase.getInstance(context)
			Log.d(TAG, "weatherDataBase instance opened")
		}

		fun destroyDatabase() {
			weatherDataBase?.let {
				WeatherDataBase.destroyInstance()
				Log.d(TAG, "weatherDataBase destroy")
			}
		}
		
		fun getCitiesNames(): Array<String>? {
			DBController.weatherDataBase?.cityModelDAO()?.getAllCities()?.let {
				return if (!it.isEmpty()) {
					val outCities: ArrayList<String> = ArrayList()
					it.forEach {
						outCities.add(it.cityName)
					}
					outCities.toTypedArray()
				} else null
			}
			return null
		}
		
		fun getCitySeasons(cityName: String): List<SeasonModel>? {
			DBController.weatherDataBase?.seasonModelDAO()?.getSeasonsForCity(cityName)?.let {
				return if (!it.isEmpty()) {
					it
				} else null
			}
			return null
		}
		
		fun getCityByName(cityName: String): CityModel? {
			DBController.weatherDataBase?.cityModelDAO()?.getCityByName(cityName)?.let {
				if (it.isNotEmpty()) return it[0]
			}
			return null
		}
		
		fun addTestDataToDatabase() {
			var cityOne = CityModel("Washington", CityType.Medium.toString())
			var cityTwo = CityModel("Miami", CityType.Big.toString())
			
			DBController.weatherDataBase?.cityModelDAO()?.getCityByName(cityOne.name)?.let {
				if (it.isEmpty()) {
					
					var cityOneSeasonOne = SeasonModel(SeasonType.Summer.toString(), cityOne.cityName)
					
					var tempMap: LinkedHashMap<String, Int> = LinkedHashMap()
					tempMap["June"] = 20
					tempMap["July"] = 25
					tempMap["August"] = 19
					cityOneSeasonOne.seasonsTempByMonth = tempMap
					
					DBController.weatherDataBase?.cityModelDAO()?.insertAll(cityOne)
					DBController.weatherDataBase?.seasonModelDAO()?.insertAll(cityOneSeasonOne)
				}
			}
			DBController.weatherDataBase?.cityModelDAO()?.getCityByName(cityTwo.name)?.let {
				if (it.isEmpty()) {
					var cityTwoSeasonOne = SeasonModel(SeasonType.Summer.toString(), cityTwo.cityName)
					
					var tempMap: LinkedHashMap<String, Int> = LinkedHashMap()
					tempMap["June"] = 30
					tempMap["July"] = 32
					tempMap["August"] = 35
					cityTwoSeasonOne.seasonsTempByMonth = tempMap
					
					DBController.weatherDataBase?.cityModelDAO()?.insertAll(cityTwo)
					DBController.weatherDataBase?.seasonModelDAO()?.insertAll(cityTwoSeasonOne)
				}
			}
			
		}
		
	}


}