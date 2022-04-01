package com.example.weather.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.weather.ui.view.DaysFragment
import com.example.weather.ui.view.TodayFragment

class ViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    companion object {
        private const val ARG_OBJECT = "object"
    }

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                TodayFragment()
            }
            1 -> {
                DaysFragment()
            }
            else -> TodayFragment()
        }
    }


}