package com.greylabs.weathercities.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import java.util.*
import kotlin.collections.LinkedHashMap

enum class SeasonType {Summer, Autumn, Winter, Spring}

@Entity(foreignKeys = [(ForeignKey(entity = CityModel::class,
		parentColumns = arrayOf("cityName"),
		childColumns = arrayOf("parentCityName"),
		onDelete = ForeignKey.CASCADE))])
class SeasonModel (var type: String, var parentCity: String) {
	@PrimaryKey(autoGenerate = true)
	var ID = 0
	
	var seasonType = type

	@TypeConverters(SeasonModelConverter::class)
	var seasonsTempByMonth: LinkedHashMap<String, Int>? = null
	
	var parentCityName = parentCity
}