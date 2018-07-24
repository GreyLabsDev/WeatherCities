package com.greylabs.weathercities.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

enum class CityType {Big, Medium, Small}

@Entity
class CityModel(var name: String, var type: String) {
	@PrimaryKey
	var cityName = name
	var cityType = type
}