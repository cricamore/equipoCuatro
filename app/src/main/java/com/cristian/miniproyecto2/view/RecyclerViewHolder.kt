package com.cristian.miniproyecto2.view

import androidx.recyclerview.widget.RecyclerView
import com.cristian.miniproyecto2.databinding.ItemInventarioBinding
import com.cristian.miniproyecto2.model.Articulo
import java.text.NumberFormat
import java.util.Locale

class RecyclerViewHolder(binding: ItemInventarioBinding): RecyclerView.ViewHolder(binding.root) {
    val bindingItem = binding

    fun setItemProducto(articulo: Articulo){
        bindingItem.nombre.text = articulo.name
        val formatPrice = NumberFormat.getNumberInstance(Locale("es", "ES")).format(articulo.price)
        bindingItem.price.text = "$ " + formatPrice
        bindingItem.id.text = articulo.id.toString()
    }
}