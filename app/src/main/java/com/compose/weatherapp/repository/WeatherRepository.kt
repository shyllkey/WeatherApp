package com.compose.weatherapp.repository

import com.compose.weatherapp.api.ApiService
import com.compose.weatherapp.data.database.WeatherDatabase
import com.compose.weatherapp.data.database.WeatherReport
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val apiService: ApiService,
    private val weatherDatabase: WeatherDatabase
) {

    private val weatherDao = weatherDatabase.weatherDao()

    suspend fun getWeatherDetails(latitude: String,
                                  longitude: String,
                                  appId: String,
                                  units: String) =
        apiService.getWeather(latitude, longitude, appId, units)


    suspend fun saveWeatherReport(weatherReport: WeatherReport){
        weatherDao.insertWeatherReport(weatherReport)
    }


    fun getWeatherReports() = weatherDao.getWeatherReports()

}