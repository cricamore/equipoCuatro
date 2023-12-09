package com.cristian.miniproyecto2.view

import androidx.recyclerview.widget.RecyclerView
import com.cristian.miniproyecto2.databinding.ItemInventarioBinding
import com.cristian.miniproyecto2.model.Articulo

class RecyclerViewHolder(binding: ItemInventarioBinding): RecyclerView.ViewHolder(binding.root) {
    val bindingItem = binding

    fun setItemProducto(articulo: Articulo){
        bindingItem.nombre.text = articulo.name
    }
}