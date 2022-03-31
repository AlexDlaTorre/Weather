package com.example.weather.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.weather.R
import com.example.weather.commons.utils.checkForInternet
import com.example.weather.databinding.FragmentSearchBinding
import com.example.weather.network.WeatherEntity
import com.example.weather.network.WeatherOneCallService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class SearchFragment : Fragment() {
    private val TAG = "MainActivityError"
    private var unit2: String = "metric"
    private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    private var latitude2 = ""
    private var longitude2 = ""
    private var id: Long = 3981467
    private var units2 = false
    private var language2 = false
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding: FragmentSearchBinding

    companion object {
        private const val ARG_OBJECT = "object"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

//        if (!checkPermissions()) {
//            requestPermissions()
//        } else {
//            getLastLocation() { location ->
//                setupViewData(location)
//            }
//        }

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        units2 = sharedPreferences.getBoolean("units", false)
        language2 = sharedPreferences.getBoolean("language", false)
    }

//    private fun setupViewData(location: Location) {
//
//        if (checkForInternet(requireContext())) {
//            // Se coloca en este punto para permitir su ejecución
//            showIndicator(true)
//            lifecycleScope.launch {
//                latitude2 = location.latitude.toString()
//                longitude2 = location.longitude.toString()
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    formatResponse(getWeather())
//                }
//            }
//        } else {
//            showError(getString(com.example.weather.R.string.no_internet_access))
//            binding.detailsContainer2.isVisible = false
//        }
//    }
//
//    private suspend fun getWeather(): WeatherEntity = withContext(Dispatchers.IO) {
//        Log.e(TAG, "CORR Lat: $latitude2 Long: $longitude2")
//
//        val retrofit: Retrofit = Retrofit.Builder()
//            .baseUrl("https://api.openweathermap.org/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        // Send arguments to weatherservice interface
//        val service: WeatherOneCallService = retrofit.create(WeatherOneCallService::class.java)
//        service.getWeatherById(id, unit2, "en", "30ba6cd1ad33ea67e2dfd78a8d28ae62")
//
//    }
//
////    class CountriesActivity : Activity() {
////        override fun onCreate(icicle: Bundle?) {
////            super.onCreate(icicle)
////            setContentView(com.example.weather.R.layout.countries)
////            val adapter = ArrayAdapter(
////                this,
////                android.R.layout.simple_dropdown_item_1line, COUNTRIES
////            )
////            val textView = findViewById<View>(com.example.weather.R.id.countries_list) as AutoCompleteTextView
////            textView.setAdapter(adapter)
////        }
////
////        companion object {
////            private val COUNTRIES = arrayOf(
////                "Belgium", "France", "Italy", "Germany", "Spain"
////            )
////        }
////    }
//
//
//    @RequiresApi(Build.VERSION_CODES.N)
//    private fun formatResponse(weatherEntity: WeatherEntity) {
//        try {
//            //Retrofit is in charge to parse our data so we can use it
//            val temp = "${weatherEntity.main.temp.toInt()}ºC"
//            val cityName = weatherEntity.name
//            val country = weatherEntity.sys.country
//            val address = "$cityName, $country"
//            val tempMin = "Mín: ${weatherEntity.main.temp_min.toInt()}ºC"
//            val tempMax = "Max: ${weatherEntity.main.temp_max.toInt()}ºC"
//            var status = ""
//            val weatherDescription = weatherEntity.weather[0].description
//            if (weatherDescription.isNotEmpty()) {
//                status = (weatherDescription[0].uppercaseChar() + weatherDescription.substring(1))
//            }
//            val dt = weatherEntity.dt
//            val updatedAt = getString(R.string.updatedAt) + android.icu.text.SimpleDateFormat(
//                "hh:mm a",
//                Locale.ENGLISH
//            ).format(Date(dt * 1000))
//            val sunrise = weatherEntity.sys.sunrise
//            val sunriseFormat =
//                android.icu.text.SimpleDateFormat("hh:mm a", Locale.ENGLISH)
//                    .format(Date(sunrise * 1000))
//            val sunset = weatherEntity.sys.sunset
//            val sunsetFormat =
//                android.icu.text.SimpleDateFormat("hh:mm a", Locale.ENGLISH)
//                    .format(Date(sunset * 1000))
//            val wind = "${weatherEntity.wind.speed} km/h"
//            val pressure = "${weatherEntity.main.pressure} mb"
//            val humidity = "${weatherEntity.main.humidity}%"
//            val feelsLike =
//                getString(R.string.sensation) + weatherEntity.main.feels_like.toInt() + "º"
//            val icon = weatherEntity.weather[0].icon
//            val iconUrl = "https://openweathermap.org/img/w/$icon.png"
//
//            binding.apply {
//                iconImageView2.load(iconUrl)
//                adressTextView2.text = address
//                dateTextView2.text = updatedAt
//                temperatureTextView2.text = temp
//                statusTextView2.text = status
//                tempMinTextView2.text = tempMin
//                tempMaxTextView2.text = tempMax
//                sunriseTextView2.text = sunriseFormat
//                sunsetTextView2.text = sunsetFormat
//                windTextView2.text = wind
//                pressureTextView2.text = pressure
//                humidityTextView2.text = humidity
//                detailsContainer2.isVisible = true
//                feelsLikeTextView2.text = feelsLike
//            }
//
//            showIndicator(false)
//        } catch (exception: Exception) {
//            showError(getString(R.string.error_ocurred))
//            Log.e("Error format", "Ha ocurrido un error")
//        }
//    }
//
//    //Toast reutilizable
//    private fun showError(message: String) {
//        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
//    }
//
//    private fun showIndicator(visible: Boolean) {
//        binding.progressBarIndicator2.isVisible = visible
//    }
//
//    @SuppressLint("MissingPermission")
//    private fun getLastLocation(onLocation: (location: Location) -> Unit) {
//        fusedLocationClient.lastLocation
//            .addOnCompleteListener { taskLocation ->
//                if (taskLocation.isSuccessful && taskLocation.result != null) {
//
//                    val location = taskLocation.result
//
//                    latitude2 = location?.latitude.toString()
//                    longitude2 = location?.longitude.toString()
//                    Log.d(TAG, "GetLasLoc Lat: $latitude2 Long: $longitude2")
//
//                    onLocation(taskLocation.result)
//                } else {
//                    Log.w(TAG, "getLastLocation:exception", taskLocation.exception)
//                    showError("No location detected")
//                }
//            }
//    }
//
//    private fun checkPermissions() =
//        ActivityCompat.checkSelfPermission(
//            requireContext(),
//            Manifest.permission.ACCESS_COARSE_LOCATION
//        ) == PermissionChecker.PERMISSION_GRANTED
//
//    private fun startLocationPermissionRequest() {
//        ActivityCompat.requestPermissions(
//            requireContext() as Activity, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
//            REQUEST_PERMISSIONS_REQUEST_CODE
//        )
//    }
//
//
//    private fun requestPermissions() {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(
//                requireContext() as Activity,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            )
//        ) {
//            // Gives an explenation to permit request. If the user denied permit but does't choose "don't ask again"
//            Log.i(
//                TAG,
//                "Location permission is needed to use the app"
//            )
////            showError(R.string.permission_rationale)
////                , android.R.string.ok) {
//            // Ask permit
////                startLocationPermissionRequest()
////            }
//
//        } else {
//            // Ask permit
//            Log.i(TAG, "Requesting permit")
//            startLocationPermissionRequest()
//        }
//    }
//
////    private fun showSnackbar(
////        snackStrId: Int,
////        actionStrId: Int = 0,
////        listener: View.OnClickListener? = null
////    ) {
////        val snackbar = Snackbar.make(
////            findViewById(android.R.id.content), getString(snackStrId),
////            BaseTransientBottomBar.LENGTH_INDEFINITE
////        )
////        if (actionStrId != 0 && listener != null) {
////            snackbar.setAction(getString(actionStrId), listener)
////        }
////        snackbar.show()
////    }
//
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        Log.i(TAG, "onRequestPermissionResult")
//        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
//            when {
//                // The flow is interrupted, the request is canceled
//                grantResults.isEmpty() -> Log.i(TAG, "The user interaction was canceled")
//
//                // Permit granted
//                (grantResults[0] == PackageManager.PERMISSION_GRANTED) -> getLastLocation(this::setupViewData)
//
//
//                else -> {
//                    showError("ultimo snack")
////                        R.string.permission_denied_explanation, R.string.settings
////                    ) {
////                        // Builds the intent that shows the window configuration of the app.
////                        val intent = Intent().apply {
////                            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
////                            data = Uri.fromParts("package", APPLICATION_ID, null)
////                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
////                        }
////                        startActivity(intent)
////                    }
//                }
//            }
//        }
//    }

}