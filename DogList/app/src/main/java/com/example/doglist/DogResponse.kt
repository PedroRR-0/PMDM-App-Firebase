package com.example.doglist

import com.google.gson.annotations.SerializedName

// Data class para el almacenamiento de datos
data class DogResponse(@SerializedName("status") var status: String,
                       @SerializedName("message") var images:List<String>)