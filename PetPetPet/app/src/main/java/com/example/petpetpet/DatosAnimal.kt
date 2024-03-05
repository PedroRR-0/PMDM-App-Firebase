package com.example.petpetpet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.petpetpet.databinding.ActivityMain2Binding
import com.example.petpetpet.databinding.DatosAnimalBinding

class DatosAnimal: AppCompatActivity() {
    private lateinit var binding: DatosAnimalBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DatosAnimalBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnVolver.setOnClickListener {
            finish()
        }
    }
}