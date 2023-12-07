package com.cristian.miniproyecto2.fragments

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cristian.miniproyecto2.R
import com.cristian.miniproyecto2.databinding.FragmentInventarioBinding
import com.cristian.miniproyecto2.view.RecyclerAdapter
import com.cristian.miniproyecto2.viewmodel.ProductoViewModel

class inventario: Fragment() {
    private lateinit var binding: FragmentInventarioBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInventarioBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler()
    }
    private val productoViewModel: ProductoViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(requireContext().applicationContext as Application)
            .create(ProductoViewModel::class.java)
    }

    fun recycler(){
        binding.btnLogout.setOnClickListener {
            findNavController().navigate(R.id.action_to_auth)
        }

        productoViewModel.getListProductos()
        var listaProductos = productoViewModel.listProductos
        val recycler = binding.recyclerview
        val navController = findNavController()
        recycler.layoutManager = LinearLayoutManager(context)
        listaProductos.observe(viewLifecycleOwner, Observer{listaProductos->
            val adapter = RecyclerAdapter(listaProductos, navController)
            recycler.adapter = adapter
            adapter.notifyDataSetChanged()
        })
    }
}