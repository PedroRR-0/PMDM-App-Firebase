package com.example.petpetpet

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.petpetpet.databinding.ActivityMain2Binding
import com.example.petpetpet.databinding.RegistroBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class Registro : AppCompatActivity(){
    private lateinit var correo: String
    private lateinit var binding: RegistroBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var pwd1:String
    private lateinit var pwd2:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        binding = RegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val btnRegister = binding.registerButton
        btnRegister.setOnClickListener(){
            correo = binding.usernameTextInputLayout.text.toString().trim()
            pwd1 = binding.contra1Re.text.toString().trim()
            pwd2 = binding.contra2Re.text.toString().trim()
            if(comprobarPasswordIguales(pwd1,pwd2)){
                creaUsuario(correo,pwd1)
            } else {
                Snackbar.make(binding.root, "Las contraseñas no coinciden", Snackbar.LENGTH_SHORT).show()
            }

        }
    }

    private fun creaUsuario(email:String, pwd:String){
        firebaseAuth.createUserWithEmailAndPassword(email,pwd)
            .addOnSuccessListener {
                agregaUsuarioDB()
            }
            .addOnFailureListener{e->
                Snackbar.make(binding.root, "Error: ${e.message}", Snackbar.LENGTH_SHORT).show()
            }
    }

    private fun agregaUsuarioDB(){
        val uid = firebaseAuth.uid
        val datosUsuario: HashMap<String,Any?> = HashMap()
        datosUsuario["uid"] = uid
        datosUsuario["email"] = correo
        datosUsuario["admin"] = false
        val referenciaDB = FirebaseDatabase.getInstance().getReference("usuarios")
        referenciaDB.child(uid!!)
            .setValue(datosUsuario)
            .addOnSuccessListener {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("usuario", correo)
                intent.putExtra("pwd", pwd1)
                startActivity(intent)
            }
            .addOnFailureListener {err ->
                Toast.makeText(this, "$err", Toast.LENGTH_SHORT).show()
            }
    }

    private fun comprobarPasswordIguales(pwd1:String,pwd2:String):Boolean{
        return pwd1==pwd2
    }
}