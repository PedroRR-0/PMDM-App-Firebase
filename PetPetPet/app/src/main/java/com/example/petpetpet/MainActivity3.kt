package com.example.petpetpet

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petpetpet.adapter.AnimalAdapter
import com.example.petpetpet.adapter.AnimalProvider
import com.example.petpetpet.databinding.ActivityMain3Binding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity3 : ComponentActivity() {

    private lateinit var binding: ActivityMain3Binding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("usuarios")
        isAdmin(firebaseAuth.uid!!)
        val intentRecup = intent
        val usuario = intentRecup.getStringExtra("usuario")
        mostrarUsuario(usuario)
        initRecyclerView()
        binding.backToMainActivity2.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            intent.putExtra("usuario", usuario)
            intent.putExtra("url", "https://img2.rtve.es/i/?w=1600&i=01709869973062.jpg")
            startActivity(intent)
        }
        onBackPressedDispatcher.addCallback(this, funPasParam)
        funPasParam.isEnabled = true

    }

    private val funPasParam = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            Toast.makeText(applicationContext, "No puede volver", Toast.LENGTH_SHORT).show()
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

    private fun isAdmin(uid: String) {
        val usrRef = databaseReference.child(uid)
        usrRef.get()
            .addOnSuccessListener {
                val admin = it.child("admin").value.toString()
                if(!admin.toBoolean()){
                    val funPasParam = object : OnBackPressedCallback(false) {
                        override fun handleOnBackPressed() {
                            Toast.makeText(applicationContext, "No puede volver", Toast.LENGTH_SHORT).show()
                        }
                    }
                    onBackPressedDispatcher.addCallback(this, funPasParam)
                    funPasParam.isEnabled = true
                    binding.backToMainActivity2.visibility= View.INVISIBLE
                }
            }.addOnFailureListener {
                Snackbar.make(binding.root, "Error", Snackbar.LENGTH_SHORT).show()
            }
    }


}
