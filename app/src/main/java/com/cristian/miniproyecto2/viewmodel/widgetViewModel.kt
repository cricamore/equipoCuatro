package com.cristian.miniproyecto2.viewmodel

import androidx.lifecycle.ViewModel
import com.cristian.miniproyecto2.repository.InventarioRepository
import com.cristian.miniproyecto2.repository.WidgetRepository

class widgetViewModel : ViewModel() {

    private val repository = WidgetRepository()
    fun calcularSumaPrecios(callback: (Double) -> Unit) {
        repository.sumaPrecios(callback)
    }
}