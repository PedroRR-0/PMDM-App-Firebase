package com.example.petpetpet.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.petpetpet.databinding.ItemAnimalBinding
import com.example.petpetpet.Animal
import com.bumptech.glide.Glide

class AnimalViewHolder(private val binding: ItemAnimalBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(animalModel: Animal) {
        binding.apply {
            tvAnimalName.text = animalModel.nombre
            tvAnimalCod.text = animalModel.cod

            Glide.with(itemView.context)
                .load(animalModel.imagen)
                .into(ivAnimal)
        }
    }
}
