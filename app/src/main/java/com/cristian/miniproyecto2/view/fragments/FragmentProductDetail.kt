package com.cristian.miniproyecto2.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.cristian.miniproyecto2.R
import com.cristian.miniproyecto2.databinding.FragmentProductDetailBinding
import com.cristian.miniproyecto2.viewmodel.InventarioViewModel
import com.google.firebase.firestore.FirebaseFirestore
import java.text.NumberFormat
import java.util.Locale

class FragmentProductDetail : Fragment() {

    private val viewModel : InventarioViewModel by viewModels()
    lateinit var binding : FragmentProductDetailBinding
    lateinit var nameArticulo : String
    var idArticulo : Long = 0
    var priceArticulo : Long = 0
    var quantityArticulo : Long = 0
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        idArticulo = arguments?.getLong("idArticulo") ?: 0
        nameArticulo = arguments?.getString("nameArticulo") ?: "No hay articulo"
        priceArticulo = arguments?.getLong("priceArticulo") ?: 0
        quantityArticulo = arguments?.getLong("quantityArticulo") ?: 0
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.action_fragmentProductDetail_to_fragmentInventario)
        }
        show()
        editAndDelete()
    }

    fun show(){
        binding.productName.text = nameArticulo
        val priceTemplate = getString(R.string.price_template)

        val pricePoints = NumberFormat.getNumberInstance(Locale("es", "ES")).format(priceArticulo)
        val formattedPrice = String.format(priceTemplate, pricePoints)
        binding.priceValue.text = formattedPrice
        binding.quantityValue.text = quantityArticulo.toString()

        val total = priceArticulo * quantityArticulo
        val totalPoints = NumberFormat.getNumberInstance(Locale("es", "ES")).format(total)
        val formattedTotal = String.format(priceTemplate, totalPoints)
        binding.totalValue.text = formattedTotal

    }

    fun editAndDelete(){

        binding.btnDelete.setOnClickListener{
            viewModel.eliminarArticulo(idArticulo.toString(), requireContext())
            findNavController().navigate(R.id.action_fragmentProductDetail_to_fragmentInventario)
        }

        binding.btnEdit.setOnClickListener{
            val bundle = Bundle().apply {
                putLong("idArticulo", idArticulo)
                putString("nameArticulo", nameArticulo)
                putLong("priceArticulo", priceArticulo)
                putLong("quantityArticulo", quantityArticulo)
            }
            findNavController().navigate(R.id.action_fragmentProductDetail_to_fragmentEditProduct, bundle)
        }
    }
}