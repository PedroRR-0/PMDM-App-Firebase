package com.example.doglist

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doglist.databinding.ActivityMainBinding
import com.example.doglist.adapter.DogAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale

// Declaración de la clase MainActivity, que hereda de AppCompatActivity
// e implementa la interfaz OnQueryTextListener para manejar eventos de búsqueda de SearchView
class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    // Declaración de variables miembro de la clase
    private lateinit var binding: ActivityMainBinding // Variable para acceder a los elementos de la interfaz de usuario mediante view binding
    private lateinit var adapter: DogAdapter // Adaptador para el RecyclerView
    private val dogImages = mutableListOf<String>() // Lista mutable para almacenar URL de imágenes de perros

    // Método onCreate, que se llama cuando se crea la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflar el diseño de la actividad utilizando view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root) // Establecer el contenido de la actividad como el diseño inflado

        // Establecer un listener para el SearchView
        binding.svDogs.setOnQueryTextListener(this)

        // Inicializar el RecyclerView
        initReciclerview()
    }

    // Método privado para inicializar el RecyclerView
    private fun initReciclerview(){
        // Crear un nuevo adaptador para el RecyclerView
        adapter = DogAdapter(dogImages)
        // Establecer el layout manager y el adaptador para el RecyclerView
        binding.rvDogs.layoutManager = LinearLayoutManager(this)
        binding.rvDogs.adapter = adapter
    }

    // Método privado para obtener una instancia de Retrofit
    private fun getRetrofit(): Retrofit {
        // Configurar y construir un objeto Retrofit
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Método privado para realizar una búsqueda de imágenes de perros por nombre
    @SuppressLint("NotifyDataSetChanged")
    private fun searchByName(query:String){
        // Ejecutar la solicitud de red en un hilo de fondo utilizando Coroutines
        CoroutineScope(Dispatchers.IO).launch {
            // Realizar la llamada a la API utilizando Retrofit para obtener las imágenes de perros por nombre
            val call = getRetrofit().create(APIService::class.java).getDogsByBreeds("$query/images")
            // Obtener la respuesta de la llamada HTTP
            val puppies = call.body()
            // Ejecutar la actualización de la interfaz de usuario en el hilo principal
            runOnUiThread{
                if (call.isSuccessful){
                    // Si la llamada fue exitosa, actualizar la lista de imágenes y notificar al adaptador
                    val images = puppies?.images ?: emptyList()
                    dogImages.clear()
                    dogImages.addAll(images)
                    adapter.notifyDataSetChanged()
                }else{
                    // Si la llamada no fue exitosa, mostrar un mensaje de error
                    showError()
                }
                // Ocultar el teclado virtual después de la búsqueda
                hideKeyboard()
            }
        }
    }

    // Método privado para ocultar el teclado
    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }

    // Método privado para mostrar un mensaje de error
    private fun showError() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_LONG).show()
    }

    // Método de la interfaz OnQueryTextListener para manejar la presentación de la consulta de búsqueda
    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
            // Si la consulta no está vacía, realiza la búsqueda de imágenes por nombre
            searchByName(query.lowercase(Locale.ROOT))
        }
        return true
    }

    // Método de la interfaz OnQueryTextListener para manejar el cambio de texto en el SearchView
    override fun onQueryTextChange(newText: String?): Boolean {
        // No se utiliza
        return true
    }
}

