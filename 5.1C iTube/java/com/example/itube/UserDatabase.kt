package com.example.itube

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.itube.UserDAO

@Database(entities = [User::class], version = 1)
@TypeConverters(Converters::class) // Add this line to include the TypeConverter
abstract class UserDatabase: RoomDatabase() {

    abstract fun userDao(): UserDAO

}