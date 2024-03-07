package com.example.petpetpet.adapter

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.petpetpet.databinding.ItemAnimalBinding
import com.example.petpetpet.Animal
import com.bumptech.glide.Glide
import com.example.petpetpet.Animal2
import com.example.petpetpet.DatosAnimal
import com.example.petpetpet.MainActivity
import com.example.petpetpet.MainActivity2

class AnimalViewHolder(private val binding: ItemAnimalBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(animalModel: Animal2) {
        binding.apply {
            tvAnimalName.text = animalModel.nombre
            tvAnimalCod.text = animalModel.codId

            /*Glide.with(itemView.context)
                .load(animalModel.imagen)
                .into(ivAnimal)*/
        }

        binding.btnMas.setOnClickListener{
            val intent = Intent(itemView.context, DatosAnimal::class.java)
            intent.putExtra("id", animalModel.codId)
            itemView.context.startActivity(intent)
        }

    }
}
