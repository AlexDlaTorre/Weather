package com.example.weather.model.one

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.databinding.ItemDaysBinding


class DaysAdapter
    (val activity: Activity, val personajes: ArrayList<Daily>): RecyclerView.Adapter<DaysAdapter.DaysHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DaysAdapter.DaysHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_days,parent,false)
        return DaysHolder(view)
    }

    override fun onBindViewHolder(holder: DaysAdapter.DaysHolder, position: Int) {
        val personaje = personajes.get(position)
        val tempMin = "Mín: ${personaje.temp?.min?.toInt()}ºC"
        val tempMax = "Max: ${personaje.temp?.max?.toInt()}ºC"
        with(holder) {
            binding.apply {
                textViewtempmin.text = tempMin.toString()
                textViewtempmax.text = tempMax.toString()
            }
        }
    }

    override fun getItemCount(): Int = personajes.size

    class DaysHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = ItemDaysBinding.bind(view)
    }

}