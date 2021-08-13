package com.compose.weatherapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherReport(weatherReport: WeatherReport)

    @Query("SELECT * FROM weather_reports ORDER BY time DESC")
    fun getWeatherReports() : Flow<List<WeatherReport>>

}