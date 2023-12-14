package com.cristian.miniproyecto2.view.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.cristian.miniproyecto2.R
import com.cristian.miniproyecto2.databinding.FragmentEditProductBinding
import com.cristian.miniproyecto2.viewmodel.InventarioViewModel
import com.google.firebase.firestore.FirebaseFirestore
import java.text.NumberFormat
import java.util.Locale
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentEditProduct : Fragment() {
    lateinit var binding : FragmentEditProductBinding
    private val viewModel : InventarioViewModel by viewModels()

    lateinit var nameArticulo : String
    var idArticulo : Long = 0
    var priceArticulo : Double = 0.0
    var quantityArticulo : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditProductBinding.inflate(inflater)
        binding.lifecycleOwner = this
        idArticulo = arguments?.getLong("idArticulo") ?: 0
        nameArticulo = arguments?.getString("nameArticulo") ?: "No hay articulo"
        priceArticulo = arguments?.getDouble("priceArticulo") ?: 0.0
        quantityArticulo = arguments?.getLong("quantityArticulo") ?: 0
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = Bundle().apply {
            putLong("idArticulo", idArticulo)
            putString("nameArticulo", nameArticulo)
            putDouble("priceArticulo", priceArticulo)
            putLong("quantityArticulo", quantityArticulo)
        }
        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.action_fragmentEditProduct_to_fragmentProductDetail, bundle)
        }

        binding.nombreArticulo.addTextChangedListener(textWatcher)
        binding.precioArticulo.addTextChangedListener(textWatcher)
        binding.cantidadArticulo.addTextChangedListener(textWatcher)

        show()
        edit()
    }

    fun edit(){
        // Verificar si alguno de los campos está vacío
        val nombreArticulo = binding.nombreArticulo.text.toString()
        val precioArticulo = binding.precioArticulo.text.toString()
        val cantidadArticulo = binding.cantidadArticulo.text.toString()

        // Configuración del onClickListener del botón de Editar
        binding.btnEditar.setOnClickListener {
            //Toast.makeText(requireContext(), "Boton editar", Toast.LENGTH_LONG).show()
            saveObject()
            findNavController().navigate(R.id.action_fragmentEditProduct_to_fragmentInventario)
        }
    }

    fun saveObject(){
        //val productoRef = db.collection("articulo").document(idArticulo.toString())

        val nombreArticulo = binding.nombreArticulo.text.toString()
        val precioArticulo = binding.precioArticulo.text.toString().toDouble()
        val cantidadArticulo = binding.cantidadArticulo.text.toString().toLong()

        viewModel.editarArticulo(idArticulo.toString(), nombreArticulo, precioArticulo, cantidadArticulo, requireContext())
    }

    fun show() {
        binding.tvIdProduct.text = "Id: " + idArticulo.toString()
        binding.nombreArticulo.setText(nameArticulo) // Asigna directamente el texto sin castings
        val formattedPrice = NumberFormat.getNumberInstance(Locale("es", "ES")).format(priceArticulo)
        binding.precioArticulo.setText(String.format("%s,00", formattedPrice))
        //binding.precioArticulo.setText(priceArticulo.toString()) // Convierte a String y establece el texto
        binding.cantidadArticulo.setText(quantityArticulo.toString()) // Convierte a String y establece el texto
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            val nombreArticulo = binding.nombreArticulo.text.toString()
            val precioArticulo = binding.precioArticulo.text.toString()
            val cantidadArticulo = binding.cantidadArticulo.text.toString()

            val algunCampoVacio = nombreArticulo.isEmpty() || precioArticulo.isEmpty() || cantidadArticulo.isEmpty()


            binding.btnEditar.isEnabled = !algunCampoVacio
        }
    }

}