package com.example.petpetpet

import android.annotation.SuppressLint
import android.content.Context

class AnimalProvider {
    companion object{
        @SuppressLint("Range")
        fun getAnimalListFromDatabase(context: Context): List<Animal> {
            val dbHelper = BaseDatos(context)
            val db = dbHelper.readableDatabase
            val animalList = mutableListOf<Animal>()

            val cursor = db.rawQuery("SELECT * FROM Animal", null)

            while (cursor.moveToNext()) {
                val cod = cursor.getString(cursor.getColumnIndex("cod"))
                val nombre = cursor.getString(cursor.getColumnIndex("nombre"))
                val raza = cursor.getString(cursor.getColumnIndex("raza"))
                val sexo = cursor.getString(cursor.getColumnIndex("sexo"))
                val fechaNacimiento = cursor.getString(cursor.getColumnIndex("fechaNacimiento"))
                val dni = cursor.getString(cursor.getColumnIndex("dni"))
                val imagen = cursor.getBlob(cursor.getColumnIndex("imagen"))
                val animal = Animal(cod, nombre, raza, sexo, fechaNacimiento, dni, imagen)
                animalList.add(animal)
            }

            cursor.close()
            db.close()

            return animalList
        }
    }
}
