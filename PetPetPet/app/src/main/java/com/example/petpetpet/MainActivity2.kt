package com.example.petpetpet

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.petpetpet.databinding.ActivityMain2Binding
import java.io.ByteArrayOutputStream
import kotlin.random.Random

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private lateinit var db: BaseDatos

    private val funPasParam = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            Toast.makeText(applicationContext, "No puede volver", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        db = BaseDatos(this)

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
        val textViewUsuario: TextView = binding.textViewUsuario
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
            val imagen = stream.toByteArray()

            val resultado = db.insertarAnimal(codId, nombre, raza, sexo, fechaNac, dni, imagen)

            if (resultado) {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error al registrar el animal", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun activarBtnMod() {
        val buttonModifica: Button = binding.modifica
        buttonModifica.setOnClickListener {
            val codId = binding.CodId.text.toString()
            val nombre = binding.Nombre.text.toString()
            val raza = binding.Raza.text.toString()
            val sexo = binding.Sexo.text.toString()
            val fechaNac = binding.FechaNac.text.toString()
            val dni = binding.DNI.text.toString()

                val resultado = db.modificarAnimal(codId, nombre, raza, sexo, fechaNac, dni)

                if (resultado) {
                    Toast.makeText(this, "Modificación exitosa", Toast.LENGTH_SHORT).show()
                } else {
                }
        }
    }

    private fun activarBtnBorrar() {
        val buttonBorra: Button = binding.borra
        buttonBorra.setOnClickListener {
            val codId = binding.CodId.text.toString()

            val resultado = db.borrarAnimal(codId)

            if (resultado) {
                Toast.makeText(this, "Borrado exitoso", Toast.LENGTH_SHORT).show()
                binding.CodId.text?.clear()
                binding.Nombre.text?.clear()
                binding.Raza.text?.clear()
                binding.Sexo.text?.clear()
                binding.FechaNac.text?.clear()
                binding.DNI.text?.clear()
            } else {
                Toast.makeText(this, "Error al borrar el animal", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun activarBtnConsulta() {
        val buttonConsulta: Button = binding.consulta
        buttonConsulta.setOnClickListener {
            val codId = binding.CodId.text.toString()

            val animal = db.consultarAnimal(codId)
            Toast.makeText(this, "Error al modificar el animal", Toast.LENGTH_SHORT).show()


            if (animal != null) {
                binding.Nombre.setText(animal.nombre)
                binding.Raza.setText(animal.raza)
                binding.Sexo.setText(animal.sexo)
                binding.FechaNac.setText(animal.fechaNac)
                binding.DNI.setText(animal.dni)

                Toast.makeText(this, "Consulta exitosa", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No se encontró el animal con el código $codId", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun activarBtnConsTodas() {
        val buttonConsulTodas: Button = binding.consulTodas
        buttonConsulTodas.setOnClickListener {
            val usuario = intent.getStringExtra("usuario")

            val intent = Intent(this, MainActivity3::class.java)
            intent.putExtra("usuario", usuario)
            startActivity(intent)
        }
    }



    override fun onDestroy() {
        db.close()
        super.onDestroy()
    }
}
