package com.example.lostandfoundapp


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.lostandfoundapp.Item
import com.example.lostandfoundapp.ItemDAO


@Database(entities = [Item::class], version = 1)
@TypeConverters(Converters::class)
abstract class ItemDatabase: RoomDatabase() {

    abstract fun itemDao(): ItemDAO

}