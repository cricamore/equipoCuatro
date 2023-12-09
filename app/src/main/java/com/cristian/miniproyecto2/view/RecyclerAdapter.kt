package com.cristian.miniproyecto2.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cristian.miniproyecto2.databinding.ItemInventarioBinding
import com.cristian.miniproyecto2.model.Articulo


class RecyclerAdapter(private val listaArticulos: MutableList<Articulo>): RecyclerView.Adapter<RecyclerViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerViewHolder {
        val context = parent.context
        val binding = ItemInventarioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val articulo = listaArticulos[position]

        return RecyclerViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return listaArticulos.size
    }

    override fun onBindViewHolder(recyclerViewHolder:RecyclerViewHolder, position: Int) {
        val articulo = listaArticulos[position]
        recyclerViewHolder.setItemProducto(articulo)
    }


}