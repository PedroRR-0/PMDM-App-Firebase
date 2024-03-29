package com.example.petpetpet

import android.content.Context
import com.google.firebase.database.*

class AnimalProvider {
    companion object {
        fun getAnimalListFromDatabase(context: Context, callback: (List<Animal2>) -> Unit) {
            val database = FirebaseDatabase.getInstance().reference
            val query: Query = database.child("animales")

            val animalList = mutableListOf<Animal2>()

            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (animalSnapshot in snapshot.children) {
                        val codId = animalSnapshot.child("codId").getValue(String::class.java)
                        val nombre = animalSnapshot.child("nombre").getValue(String::class.java)
                        val dni = animalSnapshot.child("dni").getValue(String::class.java)
                        val fechaNac = animalSnapshot.child("fechaNac").getValue(String::class.java)
                        val raza = animalSnapshot.child("raza").getValue(String::class.java)
                        val sexo = animalSnapshot.child("sexo").getValue(String::class.java)

                        val animal = Animal2(codId.toString(), nombre.toString(), dni.toString(), fechaNac.toString(), raza.toString(), sexo.toString())
                        animalList.add(animal)
                    }

                    // Llama al callback con la lista de animales una vez que se ha recorrido la base de datos
                    callback(animalList)
                }

                override fun onCancelled(error: DatabaseError) {
                    // Maneja el caso de error en la consulta
                    // Puedes agregar un manejo de errores según tus necesidades
                }
            })
        }
    }
}
