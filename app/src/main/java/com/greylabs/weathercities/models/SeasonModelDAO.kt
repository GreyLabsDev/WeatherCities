package com.greylabs.weathercities.models

import android.arch.persistence.room.*

@Dao
interface SeasonModelDAO {
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertAll(vararg seasons: SeasonModel)
	
	@Delete
	fun delete(season: SeasonModel)
	
	@Query("SELECT * FROM SeasonModel")
	fun getAllSeasons() : List<SeasonModel>
	
	@Query("SELECT * FROM SeasonModel WHERE seasonType LIKE :query")
	fun getSeasonsByType(query: String) : List<SeasonModel>
	
	@Query("SELECT * FROM SeasonModel WHERE parentCityName IS :query")
	fun getSeasonsForCity(query: String) : List<SeasonModel>
	
	@Query("DELETE FROM SeasonModel")
	fun nukeTable()
}