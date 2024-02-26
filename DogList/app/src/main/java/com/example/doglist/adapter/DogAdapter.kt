package com.example.doglist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.doglist.R

// Definición de la clase DogAdapter, que extiende RecyclerView.Adapter y recibe una lista de imágenes de perros
class DogAdapter(private val images:List<String>) : RecyclerView.Adapter<DogViewHolder>() {
    // Método llamado cuando se necesita crear un nuevo ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        // Inflar el diseño del elemento de perro utilizando un LayoutInflater
        val layoutInflater = LayoutInflater.from(parent.context)
        // Crear y devolver un nuevo DogViewHolder con el diseño inflado
        return DogViewHolder(layoutInflater.inflate(R.layout.item_dog, parent, false))
    }

    // Método que devuelve la cantidad de elementos en la lista de imágenes
    override fun getItemCount(): Int = images.size

    // Método llamado cuando se necesita enlazar datos a un ViewHolder específico
    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        // Obtener la imagen en la posición actual
        val item = images[position]
        // Llamar al método render en el ViewHolder para mostrar la imagen
        holder.render(item)
    }
}