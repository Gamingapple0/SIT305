package com.example.taskmanager

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar

var tasks = mutableListOf<Task>()

@Entity(tableName = "task")
data class Task(
    var title: String?,
    var desc: String?,
    var dueDate: Calendar? = null, // Add due date property with default value null
    @PrimaryKey(autoGenerate = true)
    val id: Int? = tasks.size
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readSerializable() as? Calendar, // Read Calendar from Parcel
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(desc)
        parcel.writeSerializable(dueDate) // Write Calendar to Parcel
        parcel.writeValue(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Task> {
        override fun createFromParcel(parcel: Parcel): Task {
            return Task(parcel)
        }

        override fun newArray(size: Int): Array<Task?> {
            return arrayOfNulls(size)
        }
    }
}
