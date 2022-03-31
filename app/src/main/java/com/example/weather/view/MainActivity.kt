package com.example.weather.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.example.weather.BuildConfig.APPLICATION_ID
import com.example.weather.R
import com.example.weather.commons.utils.checkForInternet
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.network.WeatherEntity
import com.example.weather.network.WeatherService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private val adapter by lazy { ViewPagerAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val pager = findViewById<ViewPager2>(R.id.pager)
        val tab_layout = findViewById<TabLayout>(R.id.tab_layout)
        pager.adapter = adapter
        val tabLayoutMediator = TabLayoutMediator(tab_layout,pager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when(position + 1){
                    1 -> {
                        tab.text = "Today"
                    }
                    2 -> {
                        tab.text = "5 Days"

                    }
                    3 -> {
                        tab.text = "Search"
                    }
                }
            })
        tabLayoutMediator.attach()

    }
}