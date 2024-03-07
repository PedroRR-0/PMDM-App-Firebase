package com.example.petpetpet

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petpetpet.adapter.AnimalAdapter
import com.example.petpetpet.databinding.ActivityMain3Binding

class MainActivity3 : ComponentActivity() {

    private lateinit var binding: ActivityMain3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val intentRecup = intent
        val usuario = intentRecup.getStringExtra("usuario")
        mostrarUsuario(usuario)
        initRecyclerView()
        binding.backToMainActivity2.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            intent.putExtra("usuario", usuario)
            startActivity(intent)
        }

    }

    private fun mostrarUsuario(usuario: String?) {
        val textViewUsuario = binding.textView4
        textViewUsuario.text = buildString {
        append("Usuario: ")
        append(usuario.toString())
    }
    }

    // Método para inicializar el RecyclerView
    private fun initRecyclerView() {
        binding.recyclerAnimal.layoutManager = LinearLayoutManager(this)

        // Obtener la lista de animales desde la base de datos
        AnimalProvider.getAnimalListFromDatabase(this) { animalListFromDatabase ->
            // Establecemos el adaptador del RecyclerView con la lista obtenida de la base de datos
            binding.recyclerAnimal.adapter = AnimalAdapter(animalListFromDatabase)
        }
    }


}
