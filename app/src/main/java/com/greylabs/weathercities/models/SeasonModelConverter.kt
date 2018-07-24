package com.greylabs.weathercities.models

import android.arch.persistence.room.TypeConverter
import org.json.JSONObject
import java.util.*
import kotlin.collections.LinkedHashMap

class SeasonModelConverter {
    @TypeConverter
    fun fromLinkedHashMap(inMap: LinkedHashMap<String, Int>) : String{
        var jsonObject = JSONObject(inMap)
        return jsonObject.toString()
    }

    @TypeConverter
    fun fromString(inString: String) : LinkedHashMap<String, Int> {
        var jsonObject = JSONObject(inString)
        var outMap: LinkedHashMap<String, Int> = LinkedHashMap()

        var keys = jsonObject.keys()
        keys.forEach {
            outMap[it] = jsonObject.getInt(it)
        }
        return outMap
    }
}