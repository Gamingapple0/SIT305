package com.example.lostandfoundapp

import android.location.Location
import androidx.room.TypeConverter
import java.util.Calendar

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Calendar? {
        return value?.let {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = it
            calendar
        }
    }

    @TypeConverter
    fun calendarToTimestamp(calendar: Calendar?): Long? {
        return calendar?.timeInMillis
    }

    @TypeConverter
    fun fromLocation(location: Location?): String? {
        return location?.latitude.toString() + "," + location?.longitude.toString()
    }

    @TypeConverter
    fun toLocation(locationString: String?): Location? {
        if (locationString == null) return null
        val parts = locationString.split(",")
        return if (parts.size == 2) {
            val location = Location("")
            location.latitude = parts[0].toDouble()
            location.longitude = parts[1].toDouble()
            location
        } else {
            null
        }
    }
}
