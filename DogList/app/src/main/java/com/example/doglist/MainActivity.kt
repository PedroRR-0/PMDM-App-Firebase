package com.example.doglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doglist.databinding.ActivityMainBinding
import com.example.doglist.modelo.PerroAdaptador
import com.example.doglist.modelo.ServicioAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), OnQueryTextListener {
    private lateinit var bindeo :ActivityMainBinding
    private lateinit var adapter: PerroAdaptador
    private val fotoPerro = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindeo = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindeo.root)
        bindeo.busquedaPerro.setOnQueryTextListener(this)
        lanzarRecyclerView()
    }

    private fun lanzarRecyclerView() {
        adapter = PerroAdaptador(fotoPerro, this)
        bindeo.PerrosReciclados.layoutManager = LinearLayoutManager(this)
        bindeo.PerrosReciclados.adapter = adapter

    }

    private fun cogerRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun buscarPorNombre(consulta:String){
        CoroutineScope(Dispatchers.IO).launch {
            val llamada = cogerRetrofit().create(ServicioAPI::class.java).perrosPorRaza("$consulta/images")
            val perretes = llamada.body()
            runOnUiThread{
                if(llamada.isSuccessful){
                    val imgs:List<String> = perretes?.listaImg ?: emptyList()
                    fotoPerro.clear()
                    fotoPerro.addAll(imgs)
                    adapter.notifyDataSetChanged()
                }else{
                    muestraError()
                }
            }

        }
    }

    private fun muestraError() {
        Toast.makeText(this,"Ocurrió un error",Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            buscarPorNombre(query.lowercase())
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}