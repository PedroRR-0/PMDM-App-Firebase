package com.example.petpetpet

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.petpetpet.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: SharedPreferences
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var editTextUser: TextInputLayout
    private lateinit var editTextPassword: TextInputLayout
    private lateinit var switchMaterial: Switch

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
                if (switchMaterial.isChecked) {
                    saveUserCredentials(userText, passwordText)
                } else {
                    clearUserCredentials()
                }
                //Aquí debemos pasar al siguiente activity con un intent
            }
            .addOnFailureListener {
                Snackbar.make(binding.root, "Usuario o contraseña incorrectos", Snackbar.LENGTH_SHORT).show()
            }
    }

}
