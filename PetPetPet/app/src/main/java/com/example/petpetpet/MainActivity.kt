package com.example.petpetpet

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.petpetpet.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: SharedPreferences

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val login: Button = binding.loginButton
        val editTextUser: TextInputLayout = binding.editTextUsername
        val editTextPassword: TextInputLayout = binding.editTextTextPassword
        val switchMaterial = binding.switch1

        sharedPref = getSharedPreferences("UserCredentials", Context.MODE_PRIVATE)
        val savedUser = sharedPref.getString("username", "")
        val savedPassword = sharedPref.getString("password", "")

        switchMaterial.isChecked = savedUser != "" && savedPassword != ""
        editTextUser.editText?.setText(savedUser)
        editTextPassword.editText?.setText(savedPassword)

        val db = BaseDatos(this)

        login.setOnClickListener {
            val userText = editTextUser.editText?.text.toString()
            val passwordText = editTextPassword.editText?.text.toString()

            if (db.verificarUsuarioContrasena(userText, passwordText)) {
                if (switchMaterial.isChecked) {
                    saveUserCredentials(userText, passwordText)
                    Snackbar.make(binding.root, "Credenciales guardadas", Snackbar.LENGTH_SHORT).show()
                } else {
                    clearUserCredentials()
                    Snackbar.make(binding.root, "Credenciales eliminadas", Snackbar.LENGTH_SHORT).show()
                }

                val intent = Intent(this, MainActivity2::class.java)
                intent.putExtra("usuario", userText)
                startActivity(intent)

                editTextUser.editText?.text?.clear()
                editTextPassword.editText?.text?.clear()
            } else {
                Snackbar.make(binding.root, "Usuario o contraseña incorrectos", Snackbar.LENGTH_SHORT).show()
            }
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

}
