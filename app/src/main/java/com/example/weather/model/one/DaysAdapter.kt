package com.example.weather.model.one

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R

class DaysAdapter
//    (val activity: Activity, val personajes: ArrayList<Daily>): RecyclerView.Adapter<DaysAdapter.RickAndMortyHolder>() {
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): DaysAdapter.RickAndMortyHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_days,parent,false)
//        return RickAndMortyHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: DaysAdapter.RickAndMortyHolder, position: Int) {
//        val personaje = personajes.get(position)
//        with(holder) {
//            binding.apply {
//                textViewNombre.text = personaje.name
//                imageViewFoto.load(personaje.image)
//            }
//        }
//    }
//
//    override fun getItemCount(): Int = personajes.size
//
//    class RickAndMortyHolder(view: View): RecyclerView.ViewHolder(view){
//        val binding = ItemCardviewRickandmortyBinding.bind(view)
//    }
//
//}