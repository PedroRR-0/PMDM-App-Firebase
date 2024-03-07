package com.example.petpetpet

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.petpetpet.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: SharedPreferences
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var editTextUser: TextInputLayout
    private lateinit var editTextPassword: TextInputLayout
    private lateinit var switchMaterial: Switch
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        val login: Button = binding.loginButton
        val txtRegister: TextView = binding.txtregister;
        editTextUser = binding.editTextUsername
        editTextPassword = binding.editTextTextPassword
        switchMaterial = binding.switch1
        sharedPref = getSharedPreferences("UserCredentials", Context.MODE_PRIVATE)
        val savedUser:String
        val savedPassword:String
        val usuario = intent.getStringExtra("usuario")
        val pwd = intent.getStringExtra("pwd")
        if(usuario.isNullOrBlank()){
            savedUser = sharedPref.getString("username", "").toString()
            savedPassword = sharedPref.getString("password", "").toString()
            switchMaterial.isChecked = savedUser != "" && savedPassword != ""
        } else {
            savedUser = usuario
            savedPassword = pwd.toString()
            clearUserCredentials()
        }
        editTextUser.editText?.setText(savedUser)
        editTextPassword.editText?.setText(savedPassword)
        login.setOnClickListener {
            login()
        }
        txtRegister.setOnClickListener {
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }
    }



    private fun saveUserCredentials(username: String, password: String) {
        val editor = sharedPref.edit()
        editor.putString("username", username)
        editor.putString("password", password)
        editor.apply()
    }

    private fun clearUserCredentials() {
        val editor = sharedPref.edit()
        editor.remove("username")
        editor.remove("password")
        editor.apply()
    }

    private fun login(){
        val userText = editTextUser.editText?.text.toString()
        val passwordText = editTextPassword.editText?.text.toString()
        firebaseAuth.signInWithEmailAndPassword(userText,passwordText)
            .addOnSuccessListener {
                firebaseDatabase = FirebaseDatabase.getInstance()
                databaseReference = firebaseDatabase.getReference("usuarios")
                val uid = firebaseAuth.uid
                if (switchMaterial.isChecked) {
                    saveUserCredentials(userText, passwordText)
                } else {
                    clearUserCredentials()
                }
                Toast.makeText(this,"Conectado con éxito",Toast.LENGTH_SHORT).show()
                isAdmin(uid!!,userText)
            }
            .addOnFailureListener {
                Snackbar.make(binding.root, "Usuario o contraseña incorrectos", Snackbar.LENGTH_SHORT).show()
            }
    }

    private fun isAdmin(uid: String,userText:String) {
        val usrRef = databaseReference.child(uid)
        usrRef.get()
            .addOnSuccessListener {
                val admin = it.child("admin").value.toString()
                if(admin.toBoolean()){
                    val intent = Intent(this, MainActivity2::class.java)
                    intent.putExtra("usuario", userText)
                    startActivity(intent)
                } else {
                    val intent = Intent(this, MainActivity3::class.java)
                    intent.putExtra("usuario", userText)
                    startActivity(intent)
                }
        }.addOnFailureListener {
            Snackbar.make(binding.root, "ce mnamoooooo", Snackbar.LENGTH_SHORT).show()
        }
        }
    }


