package com.greylabs.weathercities.dbtools

import android.arch.persistence.db.SupportSQLiteOpenHelper
import android.arch.persistence.room.*
import android.content.Context
import com.greylabs.weathercities.models.CityModel
import com.greylabs.weathercities.models.CityModelDAO
import com.greylabs.weathercities.models.SeasonModel
import com.greylabs.weathercities.models.SeasonModelDAO

@Database(entities = [(CityModel::class), (SeasonModel::class)], version = 4)
abstract class WeatherDataBase : RoomDatabase() {
	
	abstract fun cityModelDAO(): CityModelDAO
	abstract fun seasonModelDAO(): SeasonModelDAO
	
	companion object {
		private var INSTANCE: WeatherDataBase? = null
		
		fun getInstance(context: Context): WeatherDataBase? {
			if (INSTANCE == null) {
				synchronized(WeatherDataBase::class) {
					INSTANCE = Room.databaseBuilder(context.applicationContext,
							WeatherDataBase::class.java, "weather.db")
							.fallbackToDestructiveMigration()
							.build()
				}
			}
			return INSTANCE
		}
		
		fun destroyInstance() {
			INSTANCE = null
		}
	}
}