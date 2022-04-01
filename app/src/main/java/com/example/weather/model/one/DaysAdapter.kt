package com.example.weather.model.one

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
    (val activity: Activity, val personajes: ArrayList<Daily>) :
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
        val personaje = personajes.get(position)

        val tempMin = "Mín: ${personaje.temp?.min?.toInt()}ºC"
        val tempMax = "Max: ${personaje.temp?.max?.toInt()}ºC"
        val date = LocalDate.now()
        var dateInterval = date.plusDays(1)

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

        val formatedDays =daysOfWeek.size
       val daysIterator = daysOfWeek.get(position)

        with(holder) {
            binding.apply {
                textViewtempmin.text = tempMin.toString()
                textViewtempmax.text = tempMax.toString()
                textViewday.text = daysIterator.toString()
            }
        }
    }

    override fun getItemCount(): Int = personajes.size

    class DaysHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemDaysBinding.bind(view)
    }

}