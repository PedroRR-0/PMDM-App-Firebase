package com.example.petpetpet

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.petpetpet.databinding.DatosAnimalBinding
import com.google.firebase.Firebase
import com.google.firebase.database.*

class DatosAnimal : AppCompatActivity() {

    private lateinit var binding: DatosAnimalBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DatosAnimalBinding.inflate(layoutInflater)
        val id = intent.getStringExtra("id")
        setContentView(binding.root)

        var nombre: TextView = binding.nombreAnimal
        var idAni: TextView = binding.idAnimal
        var raza: TextView = binding.raza
        var sexo: TextView = binding.sexo
        var fechna: TextView = binding.fechNaci
        var dni: TextView = binding.dni

        database = FirebaseDatabase.getInstance().reference

        // Realiza una consulta para obtener los datos según la codId proporcionada
        val query: Query = database.child("animales").orderByChild("codId").equalTo(id)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Verifica si existe algún dato con la codId proporcionada
                if (snapshot.exists()) {
                    // Obtén el primer resultado, ya que debería ser único
                    val animalSnapshot: DataSnapshot = snapshot.children.first()

                    // Accede a los valores específicos y guárdalos en variables
                    val nombreAnimal = animalSnapshot.child("nombre").getValue(String::class.java)

                    // Asigna los valores a las vistas
                    nombre.text = nombreAnimal
                    idAni.text = id
                    raza.text = animalSnapshot.child("raza").getValue(String::class.java)
                    sexo.text = animalSnapshot.child("sexo").getValue(String::class.java)
                    fechna.text = animalSnapshot.child("fechaNac").getValue(String::class.java)
                    dni.text = animalSnapshot.child("dni").getValue(String::class.java)



                    // Puedes continuar accediendo a otros campos de la misma manera si es necesario
                } else {
                    // Maneja la situación en la que no se encuentra ningún dato con la codId proporcionada
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Maneja el caso de error en la consulta
            }
        })

        binding.btnVolver.setOnClickListener {
            finish()
        }
    }
}
