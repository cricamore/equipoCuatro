package com.cristian.miniproyecto2.viewmodel

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
        return repository.listarArticulos()
    }
}