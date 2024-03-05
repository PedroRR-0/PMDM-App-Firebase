package com.example.petpetpet

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.petpetpet.databinding.ActivityMain2Binding
import com.example.petpetpet.databinding.DatosAnimalBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class DatosAnimal: AppCompatActivity() {

    private lateinit var binding: DatosAnimalBinding
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DatosAnimalBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var nombre: TextView = binding.nombreAnimal
        database = Firebase.database.reference



        var bdnombre = (database.child("animales").child("nombre").get())
        nombre.text = bdnombre.toString()

        binding.btnVolver.setOnClickListener {
            finish()
        }
    }
}