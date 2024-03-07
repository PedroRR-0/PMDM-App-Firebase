package com.example.petpetpet

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.petpetpet.databinding.ActivityMain2Binding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import java.io.ByteArrayOutputStream
import kotlin.random.Random

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private lateinit var database: DatabaseReference

    private val funPasParam = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            Toast.makeText(applicationContext, "No puede volver", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().reference

        val usuario = intent.getStringExtra("usuario")

        mostrarUsuario(usuario)
        activarBtnAlta()
        activarBtnMod()
        activarBtnBorrar()
        activarBtnConsulta()
        activarBtnConsTodas()

        onBackPressedDispatcher.addCallback(this, funPasParam)
        funPasParam.isEnabled = true
    }

    private fun mostrarUsuario(usuario: String?) {
        val textViewUsuario = binding.textViewUsuario
        textViewUsuario.text = "Usuario: $usuario"
    }

    private fun activarBtnAlta() {
        binding.alta.setOnClickListener {
            val codId = binding.CodId.text.toString()
            val nombre = binding.Nombre.text.toString()
            val raza = binding.Raza.text.toString()
            val sexo = binding.Sexo.text.toString()
            val fechaNac = binding.FechaNac.text.toString()
            val dni = binding.DNI.text.toString()

            val imagenes = arrayOf(R.drawable.imagen1, R.drawable.imagen2, R.drawable.imagen3, R.drawable.imagen4)
            val imagenAleatoria = imagenes[Random.nextInt(imagenes.size)]

            val bitmap = BitmapFactory.decodeResource(resources, imagenAleatoria)
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

            val animal = Animal2(codId, nombre, raza, sexo, fechaNac, dni)
            guardarAnimalEnFirebase(animal)
            limpiar()
        }
    }

    private fun activarBtnMod() {
        binding.modifica.setOnClickListener {
            // Actualizar el animal en Firebase
            actualizarAnimalEnFirebase()
        }
    }

    private fun activarBtnBorrar() {
        binding.borra.setOnClickListener {
            // Obtener el código del animal a borrar
            val codId = binding.CodId.text.toString()

            // Borrar el animal en Firebase
            borrarAnimalEnFirebase(codId)
        }
    }

    private fun activarBtnConsulta() {
        binding.consulta.setOnClickListener {
            // Obtener el código del animal a consultar
            val codId = binding.CodId.text.toString()

            // Consultar el animal en Firebase
            consultarAnimalEnFirebase(codId)
        }
    }

    private fun activarBtnConsTodas() {
        binding.consulTodas.setOnClickListener {
            // Redirigir a la actividad que muestra todas las mascotas
            val usuario = intent.getStringExtra("usuario")
            val intent = Intent(this, MainActivity3::class.java)
            intent.putExtra("usuario", usuario)
            startActivity(intent)
        }
    }

    private fun guardarAnimalEnFirebase(animal: Animal2) {
        val referenciaDB = database.child("animales").push()
        referenciaDB.setValue(animal)
            .addOnSuccessListener {
                Snackbar.make(binding.root, "Registro exitoso", Snackbar.LENGTH_SHORT).show()
            }
            .addOnFailureListener { err ->
                Toast.makeText(this, "Error al registrar el animal: $err", Toast.LENGTH_SHORT).show()
            }
    }

    private fun actualizarAnimalEnFirebase() {
        val codId = binding.CodId.text.toString()
        val nombre = binding.Nombre.text.toString()
        val raza = binding.Raza.text.toString()
        val sexo = binding.Sexo.text.toString()
        val fechaNac = binding.FechaNac.text.toString()
        val dni = binding.DNI.text.toString()

        val query: Query = database.child("animales")

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var animalEncontrado = false

                for (animalSnapshot in snapshot.children) {
                    if (animalSnapshot.child("codId").getValue(String::class.java) == codId) {
                        // Actualizar los datos del animal en la base de datos
                        val referenciaDB = database.child("animales").child(animalSnapshot.key!!)
                        referenciaDB.child("nombre").setValue(nombre)
                        referenciaDB.child("raza").setValue(raza)
                        referenciaDB.child("sexo").setValue(sexo)
                        referenciaDB.child("fechaNac").setValue(fechaNac)
                        referenciaDB.child("dni").setValue(dni)

                        Snackbar.make(binding.root, "Actualización exitosa", Snackbar.LENGTH_SHORT).show()
                        animalEncontrado = true
                        limpiar()
                        break  // Salir del bucle después de encontrar y actualizar el animal
                    }
                }

                if (!animalEncontrado) {
                    Toast.makeText(this@MainActivity2, "No existe el animal con el código $codId", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Manejar el caso de error en la consulta
                Toast.makeText(this@MainActivity2, "Error al consultar la base de datos: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun borrarAnimalEnFirebase(codId: String) {
        val query: Query = database.child("animales")

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var animalEncontrado = false

                for (animalSnapshot in snapshot.children) {
                    if (animalSnapshot.child("codId").getValue(String::class.java) == codId) {
                        val referenciaDB = database.child("animales").child(animalSnapshot.key!!)
                        referenciaDB.removeValue()
                            .addOnSuccessListener {
                                Toast.makeText(this@MainActivity2, "Borrado exitoso", Toast.LENGTH_SHORT).show()
                               limpiar()
                            }
                            .addOnFailureListener { err ->
                                Toast.makeText(this@MainActivity2, "Error al borrar el animal: $err", Toast.LENGTH_SHORT).show()
                            }

                        animalEncontrado = true
                        break  // Salir del bucle después de encontrar y borrar el animal
                    }
                }

                if (!animalEncontrado) {
                    Toast.makeText(this@MainActivity2, "No existe el animal con el código $codId", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Manejar el caso de error en la consulta
                Toast.makeText(this@MainActivity2, "Error al consultar la base de datos: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun consultarAnimalEnFirebase(codId: String) {
        val database = FirebaseDatabase.getInstance().reference
        val query: Query = database.child("animales")

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var animalEncontrado = false

                for (animalSnapshot in snapshot.children) {
                    if (animalSnapshot.child("codId").getValue(String::class.java) == codId) {
                        val nombre = animalSnapshot.child("nombre").getValue(String::class.java)
                        val dni = animalSnapshot.child("dni").getValue(String::class.java)
                        val fechaNac = animalSnapshot.child("fechaNac").getValue(String::class.java)
                        val raza = animalSnapshot.child("raza").getValue(String::class.java)
                        val sexo = animalSnapshot.child("sexo").getValue(String::class.java)

                        binding.Nombre.setText(nombre)
                        binding.Raza.setText(raza)
                        binding.Sexo.setText(sexo)
                        binding.FechaNac.setText(fechaNac)
                        binding.DNI.setText(dni)

                        animalEncontrado = true
                        break  // Salir del bucle después de encontrar el animal
                    }
                }

                if (!animalEncontrado) {
                    Toast.makeText(this@MainActivity2, "No existe el animal con el código $codId", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Manejar el caso de error en la consulta
                Toast.makeText(this@MainActivity2, "Error al consultar la base de datos: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun limpiar(){
        binding.CodId.text?.clear()
        binding.Nombre.text?.clear()
        binding.Raza.text?.clear()
        binding.Sexo.text?.clear()
        binding.FechaNac.text?.clear()
        binding.DNI.text?.clear()
    }



    override fun onDestroy() {
        super.onDestroy()
    }
}
