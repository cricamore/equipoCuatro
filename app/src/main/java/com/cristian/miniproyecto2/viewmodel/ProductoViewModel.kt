package com.cristian.miniproyecto2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cristian.miniproyecto2.model.Producto
import com.cristian.miniproyecto2.repository.ProductoRepository
import kotlinx.coroutines.launch

class ProductoViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>()
    private val productoRepository = ProductoRepository(context)

    private val _listProductos = MutableLiveData<MutableList<Producto>>()
    val listProductos: LiveData<MutableList<Producto>> get() = _listProductos

    private val _progresState = MutableLiveData(false)
    val progresState: LiveData<Boolean> = _progresState

    fun saveProducto(producto: Producto) {
        viewModelScope.launch {
            _progresState.value = true
            try {
                productoRepository.saveProducto(producto)
                _progresState.value = false
            } catch (e: Exception) {
                _progresState.value = false
            }
        }
    }

    fun getListProductos() {
        viewModelScope.launch {
            _progresState.value = true
            try {
                _listProductos.value = productoRepository.getListProducto()
                _progresState.value = false
            } catch (e: Exception) {
                _progresState.value = false
            }
        }
    }

    fun deleteProducto(producto: Producto) {
        viewModelScope.launch {
            _progresState.value = true
            try {
                productoRepository.deleteProducto(producto)
                _progresState.value = false
            } catch (e: Exception) {
                _progresState.value = false
            }
        }
    }

    fun updateProducto(producto: Producto) {
        viewModelScope.launch {
            _progresState.value = true
            try {
                productoRepository.updateProducto(producto)
                _progresState.value = false
            } catch (e: Exception) {
                _progresState.value = false
            }
        }
    }
}