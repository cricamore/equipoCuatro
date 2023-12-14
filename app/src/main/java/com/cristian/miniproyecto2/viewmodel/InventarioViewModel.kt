package com.cristian.miniproyecto2.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.cristian.miniproyecto2.model.Articulo
import com.cristian.miniproyecto2.repository.InventarioRepository
import com.google.firebase.firestore.FirebaseFirestore

class InventarioViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val repository = InventarioRepository()

    fun guardarArticulo(articulo: Articulo) {
        repository.guardarArticulo(articulo)
    }

    fun listarArticulos(): MutableList<Articulo> {
        repository.guardarArticulo(Articulo(3, "Pera", 340.12, 5))
        return repository.listarArticulos()
    }

    fun editarArticulo(idArticulo: String, nombreArticulo: String, precioArticulo: Double, cantidadArticulo: Long, contexto: Context){
        repository.editarArticulo(idArticulo, nombreArticulo, precioArticulo, cantidadArticulo, contexto )
    }

    fun eliminarArticulo(idArticulo: String, contexto: Context){
        repository.eliminarArticulo(idArticulo, contexto)
    }
}