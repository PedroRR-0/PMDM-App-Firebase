package com.example.petpetpet

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.petpetpet.databinding.ActivityMain2Binding
import com.example.petpetpet.databinding.RegistroBinding

class Registro : AppCompatActivity(){
    private lateinit var binding: RegistroBinding
    private lateinit var db: BaseDatos
    val btnRegister = binding.registerButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = RegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnRegister.setOnClickListener(){
            val correo = binding.usernameTextInputLayout.text.toString()
            val contrasena = binding.contra1Re.text.toString()
            val contrasena2 = binding.contra2Re.text.toString()

            if(contrasena == contrasena2){
              //meter en la base de datos los datos y pasar al login

                //Vamos al Login pasandole el correo para poder ponerlo en el campo correo y que el usuario no tenga que volver a escribirlo
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("usuario", correo)
                startActivity(intent)
            }else{
                binding.contra2Re.error = "Las contraseñas no coinciden"
            }
        }
    }
}