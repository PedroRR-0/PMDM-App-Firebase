package com.example.petpetpet.modelo

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ServicioAPI {
    @GET
    suspend fun perrosPorRaza(@Url url:String): Response<PerroResponde>
}