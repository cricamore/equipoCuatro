package com.cristian.miniproyecto2.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.cristian.miniproyecto2.R
import com.cristian.miniproyecto2.databinding.ItemInventarioBinding
import com.cristian.miniproyecto2.model.Articulo


class RecyclerAdapter(private val listaArticulos: List<Articulo>): RecyclerView.Adapter<RecyclerViewHolder>(){

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
        recyclerViewHolder.bindingItem.cvProductItem.setOnClickListener{
            val idArticulo = listaArticulos[position].id
            val nameArticulo = listaArticulos[position].name
            val priceArticulo = listaArticulos[position].price
            val quantityArticulo = listaArticulos[position].quantity

            val bundle = Bundle().apply {
                putLong("idArticulo", idArticulo)
                putString("nameArticulo", nameArticulo)
                putDouble("priceArticulo", priceArticulo.toDouble())
                putLong("quantityArticulo", quantityArticulo)
            }

            recyclerViewHolder.itemView.findNavController().navigate(R.id.action_fragmentInventario_to_fragmentProductDetail, bundle)
        }
        recyclerViewHolder.setItemProducto(articulo)
    }



}