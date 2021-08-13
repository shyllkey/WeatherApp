package com.compose.weatherapp.viewModel

import androidx.lifecycle.*
import com.compose.weatherapp.data.database.WeatherReport
import com.compose.weatherapp.data.models.WeatherResponse
import com.compose.weatherapp.repository.WeatherRepository
import com.compose.weatherapp.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
): ViewModel() {

    private val TAG = "WeatherViewModel"


    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message

    private val _weatherDetails = MutableLiveData<WeatherResponse.Main>()
    val weatherDetails: LiveData<WeatherResponse.Main> get() = _weatherDetails

    private val _weatherList = MutableLiveData<List<WeatherReport>>()
    val weatherList: LiveData<List<WeatherReport>> get() = _weatherList


    fun getWeather(latitude :String, longitude: String,){

        viewModelScope.launch {
           val response =  weatherRepository.getWeatherDetails(
                latitude,
                longitude,
                Constants.API_KEY,
                Constants.UNITS
            )

            if (response.isSuccessful){
                response.body()?.let {
                    _weatherDetails.postValue(it.main)
                }
            }else{
                _message.postValue(response.message())
            }

        }

    }

    fun saveWeatherReport(humidity: Int, temperature: Int) {
        viewModelScope.launch {

            val weatherReport = WeatherReport(
                temperature = temperature,
                humidity = humidity,
                time = System.currentTimeMillis()
            )

//            weatherDao.insertWeatherReport(weatherReport)

            weatherRepository.saveWeatherReport(weatherReport)

        }
    }

    fun getWeatherReports() : LiveData<List<WeatherReport>>{
        return weatherRepository.getWeatherReports().asLiveData()
    }


}