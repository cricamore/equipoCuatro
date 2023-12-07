package com.cristian.miniproyecto2.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.cristian.miniproyecto2.databinding.ItemInventarioBinding
import com.cristian.miniproyecto2.model.Producto


class RecyclerAdapter(private val listaProductos: MutableList<Producto>, private val navController: NavController): RecyclerView.Adapter<RecyclerViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerViewHolder {
        val context = parent.context
        val binding = ItemInventarioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val producto = listaProductos[position]

        return RecyclerViewHolder(binding)
    }

    private fun obtenerProductoAEliminar(position: Int): String {
        return listaProductos[position].name // Suponiendo que 'producto' es el campo que deseas pasar
    }

    private fun obtenerIdAEliminar(position: Int): Int {
        return listaProductos[position].id
    }

    override fun getItemCount(): Int {
        return listaProductos.size
    }

    override fun onBindViewHolder(recyclerViewHolder:RecyclerViewHolder, position: Int) {
        val producto = listaProductos[position]

        recyclerViewHolder.setItemProducto(producto)
    }


}