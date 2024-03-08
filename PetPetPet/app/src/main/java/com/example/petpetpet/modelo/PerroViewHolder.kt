package com.example.petpetpet.modelo

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.petpetpet.MainActivity2
import com.example.petpetpet.databinding.PerroItemBinding
import com.squareup.picasso.Picasso
import kotlin.coroutines.coroutineContext

class PerroViewHolder(vista: View, private val contexto: Context, val usuario: String):RecyclerView.ViewHolder(vista) {

    private val bindeo = PerroItemBinding.bind(vista)

    fun bind(image: String) {
        Picasso.get().load(image).into(bindeo.fotoPerro)
        bindeo.fotoPerro.setOnClickListener {
            val intent = Intent(contexto, MainActivity2::class.java)
            intent.putExtra("url", image)
            intent.putExtra("usuario", usuario)
            ContextCompat.startActivity(contexto, intent, null)

        }

    }
}