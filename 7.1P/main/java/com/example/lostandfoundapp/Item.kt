package com.example.lostandfoundapp

import android.location.Location
import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat
import java.util.Calendar

var items = mutableListOf<Item>()

@Entity(tableName = "items")
data class Item(
    var name: String?,
    var phone: String?,
    var type:String?,
    var descriptption: String?,
    var date: Calendar? = null,
    var location: Location?,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = items.size
)

    : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readSerializable() as? Calendar,
        Location("").apply {
            latitude = parcel.readDouble() // Read latitude
            longitude = parcel.readDouble() // Read longitude
        },
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(phone)
        parcel.writeString(type)
        parcel.writeString(descriptption)
        parcel.writeSerializable(date)
        parcel.writeDouble(location?.latitude ?: 0.0) // Write latitude
        parcel.writeDouble(location?.longitude ?: 0.0) // Write longitude
        parcel.writeValue(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }
}
