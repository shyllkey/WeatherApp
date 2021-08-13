package com.compose.weatherapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [WeatherReport::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

}