package com.example.petpetpet

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.widget.Button
import androidx.core.app.ActivityCompat.startActivityForResult
import com.bumptech.glide.Glide
import java.io.ByteArrayOutputStream

// Nombre de la base de datos
var BD = "petpetpet"

// Clase para la gestión de la base de datos SQLite
class BaseDatos(context: Context): SQLiteOpenHelper(context, BD, null, 1) {

    companion object {
        private const val REQUEST_CODE = 123
    }

    // Método llamado al crear la base de datos por primera vez
    override fun onCreate(p0: SQLiteDatabase?) {
        // Crear la tabla Usuario con sus respectivos campos
        val crearTabla="create table Usuario( usuario VARCHAR(25) PRIMARY KEY, contrasenia VARCHAR(25))"
        p0?.execSQL(crearTabla)

        // Insertar un registro inicial de usuario y contraseña
        val usuarioInicial = "usuario"
        val contraseniaInicial = "usuario"
        val insertQuery = "INSERT INTO Usuario (usuario, contrasenia) VALUES (?, ?)"
        p0?.execSQL(insertQuery, arrayOf(usuarioInicial, contraseniaInicial))

        val crearTabla2 = "create table Animal (cod VARCHAR(25) PRIMARY KEY, nombre VARCHAR(25), raza VARCHAR(25), sexo VARCHAR(25), fechaNacimiento VARCHAR(25), dni VARCHAR(25), imagen BLOB)"
        p0?.execSQL(crearTabla2)
    }

    // Método llamado cuando se necesita actualizar la base de datos
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        // No implementado en esta versión
        TODO("Not yet implemented")
    }

    fun verificarUsuarioContrasena(usuario: String, contrasena: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Usuario WHERE usuario = ? AND contrasenia = ?", arrayOf(usuario, contrasena))
        val existe = cursor.moveToFirst()
        cursor.close()
        return existe
    }

    fun insertarAnimal(codId: String, nombre: String, raza: String, sexo: String, fechaNac: String, dni: String, imagen: ByteArray): Boolean {
        val db = this.writableDatabase

        // Crear un ContentValues para insertar los datos en la tabla Animal
        val valores = ContentValues().apply {
            put("cod", codId)
            put("nombre", nombre)
            put("raza", raza)
            put("sexo", sexo)
            put("fechaNacimiento", fechaNac)
            put("dni", dni)
            put("imagen", imagen)
        }

        // Insertar los datos en la tabla Animal
        val resultado = db.insert("Animal", null, valores) != -1L

        // Cerrar la base de datos
        db.close()

        return resultado
    }

    fun modificarAnimal(codId: String, nombre: String, raza: String, sexo: String, fechaNac: String, dni: String): Boolean {
        val db = this.writableDatabase

        // Crear un ContentValues para actualizar los datos en la tabla Animal
        val valores = ContentValues().apply {
            put("cod", codId)
            put("nombre", nombre)
            put("raza", raza)
            put("sexo", sexo)
            put("fechaNacimiento", fechaNac)
            put("dni", dni)

        }

        // Realizar la actualización en la tabla Animal
        val resultado = db.update("Animal", valores, "cod = ?", arrayOf(codId.toString())) > 0

        // Cerrar la base de datos
        db.close()

        return resultado
    }

    fun borrarAnimal(codId: String): Boolean {
        val db = this.writableDatabase

        // Realizar el borrado en la tabla Animal
        val resultado = db.delete("Animal", "cod = ?", arrayOf(codId.toString())) > 0

        // Cerrar la base de datos
        db.close()

        return resultado
    }

    @SuppressLint("Range")
    fun consultarAnimal(codId: String): Animal? {
        val db = this.readableDatabase

        // Realizar la consulta en la tabla Animal
        val cursor = db.rawQuery("SELECT * FROM Animal WHERE cod = ?", arrayOf(codId.toString()))

        // Verificar si se encontró un registro
        if (cursor.moveToFirst()) {
            // Obtener los datos del animal del cursor
            val nombre = cursor.getString(cursor.getColumnIndex("nombre"))
            val raza = cursor.getString(cursor.getColumnIndex("raza"))
            val sexo = cursor.getString(cursor.getColumnIndex("sexo"))
            val fechaNacimiento = cursor.getString(cursor.getColumnIndex("fechaNacimiento"))
            val dni = cursor.getString(cursor.getColumnIndex("dni"))
            val imagen = cursor.getBlob(cursor.getColumnIndex("imagen"))

            // Crear un objeto Animal con los datos obtenidos
            val animal = Animal(codId, nombre, raza, sexo, fechaNacimiento, dni, imagen)

            // Cerrar el cursor y la base de datos
            cursor.close()
            db.close()

            return animal
        } else {
            // Si no se encontró ningún registro, devolver null
            cursor.close()
            db.close()
            return null
        }
    }

}
