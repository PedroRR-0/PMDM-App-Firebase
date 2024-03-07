package com.example.petpetpet

class Animal {
    var codId : String = ""
    var nombre : String = ""
    var raza : String = ""
    var sexo : String = ""
    var fechaNac : String = ""
    var dni : String = ""

    constructor(cod: String, nombre: String, raza: String, sexo: String, fechaNac: String, dni: String){
        this.codId = cod
        this.nombre = nombre
        this.raza = raza
        this.sexo = sexo
        this.fechaNac = fechaNac
        this.dni = dni
    }

    constructor(){

    }
}