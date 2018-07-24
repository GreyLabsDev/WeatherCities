package com.greylabs.weathercities.models

import android.arch.persistence.room.*

@Dao
interface CityModelDAO {
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertAll(vararg cities: CityModel)
	
	@Delete
	fun delete(city: CityModel)
	
	@Query("SELECT * FROM CityModel")
	fun getAllCities() : List<CityModel>
	
	@Query("SELECT * FROM CityModel WHERE cityName LIKE :query")
	fun getCityByName(query: String) : List<CityModel>
	
	@Query("SELECT * FROM CityModel WHERE cityType LIKE :query")
	fun getCityByType(query: String) : List<CityModel>
	
	@Query("DELETE FROM CityModel")
	fun nukeTable()
}