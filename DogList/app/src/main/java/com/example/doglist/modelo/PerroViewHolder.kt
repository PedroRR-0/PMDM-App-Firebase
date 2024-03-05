package com.example.doglist.modelo

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.doglist.databinding.PerroItemBinding
import com.squareup.picasso.Picasso

class PerroViewHolder(vista: View):RecyclerView.ViewHolder(vista) {

    private val bindeo = PerroItemBinding.bind(vista)

    fun bind(image:String){
        Picasso.get().load(image).into(bindeo.fotoPerro)

    }
}