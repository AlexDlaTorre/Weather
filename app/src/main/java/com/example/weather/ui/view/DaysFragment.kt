package com.example.weather.ui.view

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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.commons.utils.checkForInternet
import com.example.weather.databinding.FragmentDaysBinding
import com.example.weather.model.days.Daily
import com.example.weather.model.days.DaysAdapter
import com.example.weather.model.days.OneEntity
import com.example.weather.network.WeatherOneCallService
import com.example.weather.ui.viewmodel.TenDaysViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class DaysFragment : Fragment() {
    private val TAG = "MainActivityError"
    private var unit: String = "metric"
    private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    private var latitude = ""
    private var longitude = ""
    private var units = false
    private var language = false
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding: FragmentDaysBinding
    private val viewmodel: TenDaysViewModel by viewModels()

    companion object {
        private const val ARG_OBJECT = "object"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDaysBinding.inflate(inflater, container, false)
        val root: View = binding.root
        observers()
        return root

    }
    private fun observers() {
        viewmodel.personajes.observe(viewLifecycleOwner,::mostrarPersonajes)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        startApp()
        observers()
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        units = sharedPreferences.getBoolean("units", false)
        language = sharedPreferences.getBoolean("language", false)
    }

    fun startApp() {
        getLastLocation() { location ->
            setupViewData(location)
        }
    }

    private fun setupViewData(location: Location) {

        if (checkForInternet(requireContext())) {
            // Se coloca en este punto para permitir su ejecución
//            showIndicator(true)
            lifecycleScope.launch {
                latitude = location.latitude.toString()
                longitude = location.longitude.toString()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    formatResponse(getWeather2())
                    println("formatResponse(getWeather2())")
                }
            }
        } else {
            showError(getString(com.example.weather.R.string.no_internet_access))

        }
    }

    private suspend fun getWeather2(): OneEntity = withContext(Dispatchers.IO) {
        Log.e(TAG, "CORR Lat: $latitude Long: $longitude")

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Send arguments to weatherservice interface
        val service: WeatherOneCallService = retrofit.create(WeatherOneCallService::class.java)
        service.getWeatherByLonLat2(
            latitude,
            longitude,
            unit,
            "en",
            "minutely,hourly",
            "30ba6cd1ad33ea67e2dfd78a8d28ae62"
        )

    }

//    class CountriesActivity : Activity() {
//        override fun onCreate(icicle: Bundle?) {
//            super.onCreate(icicle)
//            setContentView(com.example.weather.R.layout.countries)
//            val adapter = ArrayAdapter(
//                this,
//                android.R.layout.simple_dropdown_item_1line, COUNTRIES
//            )
//            val textView = findViewById<View>(com.example.weather.R.id.countries_list) as AutoCompleteTextView
//            textView.setAdapter(adapter)
//        }
//
//        companion object {
//            private val COUNTRIES = arrayOf(
//                "Belgium", "France", "Italy", "Germany", "Spain"
//            )
//        }
//    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun formatResponse(oneEntity: OneEntity) {

        println("AQUI DIARIO2 ${oneEntity.daily}")
        try {
            println("AQUI DIARIO ${oneEntity.daily}")
//            showIndicator(false)
        } catch (exception: Exception) {
            showError(getString(R.string.error_ocurred))
            Log.e("Error format", "Ha ocurrido un error")
        }
    }

    //Toast reutilizable
    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

//    private fun showIndicator(visible: Boolean) {
//        binding.progressBar_loading.isVisible = visible
//    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation(onLocation: (location: Location) -> Unit) {
        fusedLocationClient.lastLocation
            .addOnCompleteListener { taskLocation ->
                if (taskLocation.isSuccessful && taskLocation.result != null) {

                    val location = taskLocation.result

                    latitude = location?.latitude.toString()
                    longitude = location?.longitude.toString()

                    viewmodel.getForecast(
                        location.latitude,
                        location.longitude,
                        unit,
                        "en",
                        "minutely,hourly",
                        "30ba6cd1ad33ea67e2dfd78a8d28ae62"
                    )
                    Log.d(TAG, "GetLasLoc Lat: $latitude Long: $longitude")

                    onLocation(taskLocation.result)
                } else {
                    Log.w(TAG, "getLastLocation:exception", taskLocation.exception)
                    showError("No location detected")
                }
            }
    }
    private fun mostrarPersonajes(personajes: ArrayList<Daily>) {
        personajes.forEach {
            initRecycler(personajes,binding?.recyclerViewDays)
        }
    }

    private fun initRecycler(lista: ArrayList<Daily>, recyclerView: RecyclerView?){
        val adaptador = DaysAdapter(requireActivity(),lista)
        recyclerView?.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = adaptador
        }
    }

    private fun checkPermissions() =
        ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PermissionChecker.PERMISSION_GRANTED

    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(
            requireContext() as Activity, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE
        )
    }


    private fun requestPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireContext() as Activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            // Gives an explenation to permit request. If the user denied permit but does't choose "don't ask again"
            Log.i(
                TAG,
                "Location permission is needed to use the app"
            )
//            showError(R.string.permission_rationale)
//                , android.R.string.ok) {
            // Ask permit
//                startLocationPermissionRequest()
//            }

        } else {
            // Ask permit
            Log.i(TAG, "Requesting permit")
            startLocationPermissionRequest()
        }
    }

//    private fun showSnackbar(
//        snackStrId: Int,
//        actionStrId: Int = 0,
//        listener: View.OnClickListener? = null
//    ) {
//        val snackbar = Snackbar.make(
//            findViewById(android.R.id.content), getString(snackStrId),
//            BaseTransientBottomBar.LENGTH_INDEFINITE
//        )
//        if (actionStrId != 0 && listener != null) {
//            snackbar.setAction(getString(actionStrId), listener)
//        }
//        snackbar.show()
//    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            when {
                // The flow is interrupted, the request is canceled
                grantResults.isEmpty() -> Log.i(TAG, "The user interaction was canceled")

                // Permit granted
                (grantResults[0] == PackageManager.PERMISSION_GRANTED) -> getLastLocation(this::setupViewData)


                else -> {
                    showError("ultimo snack")
//                        R.string.permission_denied_explanation, R.string.settings
//                    ) {
//                        // Builds the intent that shows the window configuration of the app.
//                        val intent = Intent().apply {
//                            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
//                            data = Uri.fromParts("package", APPLICATION_ID, null)
//                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                        }
//                        startActivity(intent)
//                    }
                }
            }
        }
    }

}