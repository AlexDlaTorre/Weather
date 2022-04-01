package com.example.weather.model.days

import android.app.Activity
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.databinding.ItemDaysBinding
import java.time.LocalDate


class DaysAdapter
    (val activity: Activity, val days: ArrayList<Daily>) :
    RecyclerView.Adapter<DaysAdapter.DaysHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DaysAdapter.DaysHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_days, parent, false)
        return DaysHolder(view)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DaysAdapter.DaysHolder, position: Int) {
        val day = days.get(position)

        val tempMin = "Mín: ${day.temp?.min?.toInt()}ºC"
        val tempMax = "Max: ${day.temp?.max?.toInt()}ºC"
        val date = LocalDate.now()

        val daysOfWeek: MutableList<LocalDate> = mutableListOf(
            date,
            date.plusDays(1),
            date.plusDays(2),
            date.plusDays(3),
            date.plusDays(4),
            date.plusDays(5),
            date.plusDays(6),
            date.plusDays(7),
            date.plusDays(8)
        )

        val daysIterator = daysOfWeek.get(position)

        with(holder) {
            binding.apply {
                textViewTempMin.text = tempMin.toString()
                textViewTempMax.text = tempMax.toString()
                textViewDay.text = daysIterator.toString()
            }
        }
    }

    override fun getItemCount(): Int = days.size

    class DaysHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemDaysBinding.bind(view)
    }

}