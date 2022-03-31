package com.example.weather.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.weather.view.SevenDaysFragment
import com.example.weather.view.TenDaysFragment
import com.example.weather.view.TodayFragment

class ViewPagerAdapter (fa: FragmentActivity): FragmentStateAdapter(fa) {

    companion object{
        private const val ARG_OBJECT = "object"
    }

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                TodayFragment()
            }
            1 -> {
                SevenDaysFragment() }
            2 -> {
                TenDaysFragment()
            }
            else -> TodayFragment()
        }
    }


}