package com.example.weather.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.weather.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


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