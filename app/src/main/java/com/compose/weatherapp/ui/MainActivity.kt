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
        locationRequest()
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

        /* val request = LocationSettingsRequest.Builder()
         val settingsClient = LocationServices.getSettingsClient(this)*/
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

/*
        locationRequest = LocationRequest.create().apply {
            interval = 60000L
            fastestInterval = 1000L
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        request.addLocationRequest(locationRequest)
        val response = settingsClient.checkLocationSettings(request.build())
        response.addOnSuccessListener { startLocationUpdated() }
            .addOnFailureListener { Log.d(TAG, "locationRequest: $it") }*/


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

    override fun onStop() {
        super.onStop()
        stopLocationUpdated()
    }

    private fun checkPermission() {
        if (Utils.checkLocationPermission(this)) {
            startLocationUpdated()
        } else {
            Utils.requestLocationPermission(this)
        }
    }

    /*  private val locationCallback = object : LocationCallback() {
          override fun onLocationResult(locations: LocationResult?) {
              super.onLocationResult(locations)
              locations?.let {
                  it.locations.forEach { location ->

                  }
              }
          }
      }*/


    private fun startLocationUpdated() {

        /* if (Utils.checkLocationPermission(this)) {
             fusedLocationProviderClient.requestLocationUpdates(
                 locationRequest,
                 locationCallback,
                 Looper.getMainLooper()
             )

         }*/
    }

    private fun stopLocationUpdated() {
//        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }


}