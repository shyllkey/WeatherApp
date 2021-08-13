package com.compose.weatherapp.api

import com.compose.weatherapp.data.models.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object{
        const val WEATHER = "weather"
    }

    @GET(WEATHER)
    suspend fun getWeather(
        @Query("lat")  latitude: String,
        @Query("lon")  longitude: String,
        @Query("appid")  appId: String,
        @Query("units")  units: String
    ) : Response<WeatherResponse>

}