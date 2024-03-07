package com.example.doglist.modelo

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.doglist.MainActivity
import com.example.doglist.databinding.PerroItemBinding
import com.squareup.picasso.Picasso
import kotlin.coroutines.coroutineContext

class PerroViewHolder(vista: View, private val contexto: Context):RecyclerView.ViewHolder(vista) {

    private val bindeo = PerroItemBinding.bind(vista)

    fun bind(image:String){
        Picasso.get().load(image).into(bindeo.fotoPerro)
        bindeo.fotoPerro.setOnClickListener{
            Toast.makeText(contexto,image,Toast.LENGTH_SHORT).show()
        }
    }

}