package com.example.petpetpet

import java.util.Date

class Animal {
    var cod : String = ""
    var nombre : String = ""
    var raza : String = ""
    var sexo : String = ""
    var fechaNac : String = ""
    var dni : String = ""
    var imagen : ByteArray = byteArrayOf()

    constructor(cod: String, nombre: String, raza: String, sexo: String, fechaNac: String, dni: String, imagen: ByteArray){
        this.cod = cod
        this.nombre = nombre
        this.raza = raza
        this.sexo = sexo
        this.fechaNac = fechaNac
        this.dni = dni
        this.imagen = imagen
    }

    constructor(){

    }
}