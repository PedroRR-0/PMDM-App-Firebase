package com.example.doglist

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

// Interfaz para interactuar con la API de perros
interface APIService {
    // Método para obtener imágenes de perros por raza
    @GET
    suspend fun getDogsByBreeds(@Url url:String): Response <DogResponse>
}