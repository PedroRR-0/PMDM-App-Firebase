package com.example.petpetpet.modelo

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petpetpet.R

class PerroAdaptador(val imagenes:List<String>, val context:Context, val usuario: String):RecyclerView.Adapter<PerroViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerroViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PerroViewHolder(layoutInflater.inflate(R.layout.perro_item,parent,false),context, usuario)
    }

    override fun getItemCount(): Int = imagenes.size

    override fun onBindViewHolder(holder: PerroViewHolder, position: Int) {
        val elem:String = imagenes[position]
        holder.bind(elem)
    }
}