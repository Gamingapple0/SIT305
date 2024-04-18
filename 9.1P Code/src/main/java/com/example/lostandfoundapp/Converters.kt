package com.example.lostandfoundapp

import android.location.Location
import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng
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
    fun fromLatLng(location: LatLng?): String? {
        return location?.latitude.toString() + "," + location?.longitude.toString()
    }

    @TypeConverter
    fun toLatLng(locationString: String?): LatLng? {
        if (locationString == null) return null
        val parts = locationString.split(",")
        return if (parts.size == 2) {
            LatLng(parts[0].toDouble(), parts[1].toDouble())
        } else {
            null
        }
    }
}
