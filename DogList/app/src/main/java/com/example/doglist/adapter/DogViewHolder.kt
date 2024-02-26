package com.example.doglist.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.doglist.databinding.ItemDogBinding
import com.squareup.picasso.Picasso

// Definición de la clase DogViewHolder, que extiende RecyclerView.ViewHolder
class DogViewHolder(view: View):RecyclerView.ViewHolder(view) {

    // Variable para acceder a los elementos de la vista mediante view binding
    val binding = ItemDogBinding.bind(view)

    // Método para renderizar la imagen del perro en el ViewHolder
    fun render(image: String) {
        // Cargar la imagen desde la URL utilizando Picasso y establecerla en el ImageView
        Picasso.get().load(image).into(binding.ivDog)
    }

}