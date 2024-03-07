package com.example.petpetpet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petpetpet.Animal2
import com.example.petpetpet.databinding.ItemAnimalBinding


class AnimalAdapter(private val animalList: List<Animal2>) : RecyclerView.Adapter<AnimalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAnimalBinding.inflate(inflater, parent, false)
        return AnimalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        val item = animalList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = animalList.size
}
