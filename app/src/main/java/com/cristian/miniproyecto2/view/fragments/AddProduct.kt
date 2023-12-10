package com.cristian.miniproyecto2.view.fragments

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.cristian.miniproyecto2.R
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cristian.miniproyecto2.databinding.FragmentAddProductBinding
import androidx.core.content.ContextCompat
import com.cristian.miniproyecto2.model.Articulo
import androidx.fragment.app.viewModels
import com.cristian.miniproyecto2.viewmodel.InventarioViewModel




class AddProduct : Fragment() {
    private lateinit var binding: FragmentAddProductBinding
    private val inventarioViewModel: InventarioViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddProductBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    private fun controllers() {

        binding.backButton.setOnClickListener{
            findNavController().navigate(R.id.action_fragmentAddProduct_to_inventario)

        }

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.guardarButton.isEnabled = false
        binding.guardarButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
        binding.guardarButton.setTypeface(null, Typeface.NORMAL)

        // Agrega TextWatchers para cada campo y actualiza el estado del botón
        binding.codigoEditText.addTextChangedListener { actualizarEstadoBoton() }
        binding.nombreEditText.addTextChangedListener { actualizarEstadoBoton() }
        binding.precioEditText.addTextChangedListener { actualizarEstadoBoton() }
        binding.cantidadEditText.addTextChangedListener { actualizarEstadoBoton() }

        binding.guardarButton.setOnClickListener {
            guardarProducto()
        }
        controllers()

    }

    private fun actualizarEstadoBoton() {
        val codigo = binding.codigoEditText.text.toString().trim()
        val nombre = binding.nombreEditText.text.toString().trim()
        val precio = binding.precioEditText.text.toString().trim()
        val cantidad = binding.cantidadEditText.text.toString().trim()

        val camposLlenos = codigo.isNotEmpty() && nombre.isNotEmpty() && precio.isNotEmpty() && cantidad.isNotEmpty()

        binding.guardarButton.isEnabled = camposLlenos
        binding.guardarButton.setTextColor(if (camposLlenos) ContextCompat.getColor(requireContext(), R.color.white) else ContextCompat.getColor(requireContext(), R.color.gray))
        binding.guardarButton.setTypeface(null, if (camposLlenos) Typeface.BOLD else Typeface.NORMAL)
    }

    private fun guardarProducto() {
        val codigo = binding.codigoEditText.text.toString().trim()
        val nombre = binding.nombreEditText.text.toString().trim()
        val precio = binding.precioEditText.text.toString().trim().toLong()
        val cantidad = binding.cantidadEditText.text.toString().trim().toLong()

        val nuevoArticulo = Articulo(
            id = System.currentTimeMillis(), // Use a unique identifier (e.g., timestamp) as ID
            name = nombre,
            price = precio,
            quantity = cantidad
        )

        inventarioViewModel.guardarArticulo(nuevoArticulo)

        findNavController().navigate(R.id.action_fragmentAddProduct_to_inventario)
    }

}
