package com.example.doglist.modelo

import com.google.gson.annotations.SerializedName

data class PerroResponde(@SerializedName("status") var estado:String,
                         @SerializedName("message") var listaImg:List<String>)
