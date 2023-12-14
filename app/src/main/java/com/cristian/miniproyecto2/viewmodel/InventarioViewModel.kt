package com.cristian.miniproyecto2.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cristian.miniproyecto2.model.Articulo
import com.cristian.miniproyecto2.repository.InventarioRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InventarioViewModel @Inject constructor(
    private val repository : InventarioRepository
): ViewModel() {

    fun guardarArticulo(articulo: Articulo) {
        repository.guardarArticulo(articulo)
    }

    fun listarArticulos(callback: (List<Articulo>) -> Unit) {
        repository.listarArticulos(callback)
    }

    fun editarArticulo(idArticulo: String, nombreArticulo: String, precioArticulo: Double, cantidadArticulo: Long, contexto: Context){
        repository.editarArticulo(idArticulo, nombreArticulo, precioArticulo, cantidadArticulo, contexto )
    }

    fun eliminarArticulo(idArticulo: String, contexto: Context){
        repository.eliminarArticulo(idArticulo, contexto)
    }

}