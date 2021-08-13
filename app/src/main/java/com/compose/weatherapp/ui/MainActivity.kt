package com.compose.weatherapp.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.compose.weatherapp.databinding.ActivityMainBinding
import com.compose.weatherapp.utils.Utils
import com.compose.weatherapp.viewModel.WeatherViewModel
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private val viewModel: WeatherViewModel by viewModels()
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    var weatherAdapter: WeatherAdapter = WeatherAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initValues()
        observers()


    }

    private fun initValues() {
        binding.recyclerView.apply { adapter = weatherAdapter }
        binding.swipe.setOnRefreshListener {
            locationRequest()
        }
    }


    private fun observers() {

        with(viewModel) {

            message.observe(this@MainActivity, {
                Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()

            })

            weatherDetails.observe(this@MainActivity, {
                val humidity = it.humidity
                val temperature = it.temp.toInt()
                saveWeatherReport(humidity, temperature)

                Toast.makeText(this@MainActivity, humidity.toString(), Toast.LENGTH_SHORT).show()

            })

            getWeatherReports().observe(this@MainActivity, {
                weatherAdapter.submitList(it)
            })


        }

    }

    private fun setRefreshFalse() {
        binding.swipe.isRefreshing = false
    }

    private fun locationRequest() {

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        if (Utils.checkLocationPermission(this)) {
            val lastLocation = fusedLocationProviderClient.lastLocation
            lastLocation.addOnSuccessListener {
                setRefreshFalse()
                viewModel.getWeather(it?.latitude.toString(), it?.longitude.toString())
            }.addOnFailureListener {
                    Log.d(TAG, "locationRequest: $it")
                    setRefreshFalse()
                }

        }



    }

    override fun onStart() {
        super.onStart()
        checkPermission()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            checkPermission()
        }

    }

    private fun checkPermission() {
        if (Utils.checkLocationPermission(this)) {
            locationRequest()
        } else {
            Utils.requestLocationPermission(this)
        }
    }




}