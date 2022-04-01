package com.example.weather.ui.view

import android.annotation.SuppressLint
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.commons.utils.checkForInternet
import com.example.weather.databinding.FragmentDaysBinding
import com.example.weather.model.days.Daily
import com.example.weather.model.days.DaysAdapter
import com.example.weather.model.days.OneEntity
import com.example.weather.network.WeatherOneCallService
import com.example.weather.ui.viewmodel.DaysViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DaysFragment : Fragment() {
    private val TAG = "DaysFragmentError"
    private var unit: String = "metric"
    private var latitude = ""
    private var longitude = ""
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding: FragmentDaysBinding
    private val viewmodel: DaysViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDaysBinding.inflate(inflater, container, false)
        val root: View = binding.root
        observers()
        return root
    }

    private fun observers() {
        viewmodel.daysList.observe(viewLifecycleOwner, ::showDays)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        startApp()
        observers()
    }

    fun startApp() {
        getLastLocation { location ->
            setupViewData(location)
        }
    }

    private fun setupViewData(location: Location) {

        if (checkForInternet(requireContext())) {

            lifecycleScope.launch {
                latitude = location.latitude.toString()
                longitude = location.longitude.toString()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    getWeatherByLonLat2()
                }
            }
        } else {
            showError(getString(com.example.weather.R.string.no_internet_access))

        }
    }

    private suspend fun getWeatherByLonLat2(): OneEntity = withContext(Dispatchers.IO) {
        Log.e(TAG, "CORR Lat: $latitude Long: $longitude")

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: WeatherOneCallService = retrofit.create(WeatherOneCallService::class.java)
        service.getWeatherByLonLat2(
            latitude,
            longitude,
            "metric",
            "en",
            "minutely,hourly",
            "30ba6cd1ad33ea67e2dfd78a8d28ae62"
        )

    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

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
                    onLocation(taskLocation.result)
                } else {
                    Log.w(TAG, "getLastLocation:exception", taskLocation.exception)
                    showError("No location detected")
                }
            }
    }

    private fun showDays(personajes: ArrayList<Daily>) {
        personajes.forEach {
            initRecycler(personajes, binding.recyclerViewDays)
        }
    }

    private fun initRecycler(lista: ArrayList<Daily>, recyclerView: RecyclerView?) {
        val adaptador = DaysAdapter(requireActivity(), lista)
        recyclerView?.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = adaptador
        }
    }

}