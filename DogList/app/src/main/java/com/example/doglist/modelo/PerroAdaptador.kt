package com.example.doglist.modelo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.doglist.R

class PerroAdaptador(val imagenes:List<String>):RecyclerView.Adapter<PerroViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerroViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PerroViewHolder(layoutInflater.inflate(R.layout.perro_item,parent,false))
    }

    override fun getItemCount(): Int = imagenes.size

    override fun onBindViewHolder(holder: PerroViewHolder, position: Int) {
        val elem:String = imagenes[position]
        holder.bind(elem)
    }
}