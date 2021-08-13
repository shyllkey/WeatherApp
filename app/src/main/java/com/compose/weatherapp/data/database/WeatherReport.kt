package com.compose.weatherapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_reports")
data class WeatherReport(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var temperature: Int?,
    var humidity: Int,
    var time: Long
)

