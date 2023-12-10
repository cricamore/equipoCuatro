package com.cristian.miniproyecto2.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.cristian.miniproyecto2.R
import com.google.android.material.textfield.TextInputEditText
import androidx.fragment.app.Fragment

class AddProduct : Fragment() {

    private lateinit var codigoEditText: TextInputEditText
    private lateinit var nombreEditText: TextInputEditText
    private lateinit var precioEditText: TextInputEditText
    private lateinit var cantidadEditText: TextInputEditText
    private lateinit var guardarButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_product, container, false)

        // Obtener referencias a los elementos de la interfaz de usuario
        codigoEditText = view.findViewById(R.id.codigoEditText)
        nombreEditText = view.findViewById(R.id.nombreEditText)
        precioEditText = view.findViewById(R.id.precioEditText)
        cantidadEditText = view.findViewById(R.id.cantidadEditText)
        guardarButton = view.findViewById(R.id.guardarButton)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar el evento de clic del botón "Guardar"
        guardarButton.setOnClickListener {
            // Acciones que se realizarán al hacer clic en el botón "Guardar"
            guardarProducto()
        }
    }

    private fun guardarProducto() {
        // Obtener los valores ingresados por el usuario
        val codigo = codigoEditText.text.toString()
        val nombre = nombreEditText.text.toString()
        val precio = precioEditText.text.toString()
        val cantidad = cantidadEditText.text.toString()

        // Realizar acciones para guardar el producto en la base de datos o donde sea necesario
        // Puedes utilizar la lógica de tu función guardarArticulo() aquí
    }
}
