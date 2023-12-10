package com.cristian.miniproyecto2.view.fragments

//import android.content.Intent.getIntent
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cristian.miniproyecto2.R
import com.cristian.miniproyecto2.databinding.FragmentInventarioBinding
import com.cristian.miniproyecto2.view.RecyclerAdapter
import com.cristian.miniproyecto2.viewmodel.InventarioViewModel
import com.google.firebase.firestore.FirebaseFirestore

class inventario : Fragment() {
    private lateinit var binding: FragmentInventarioBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val inventarioViewModel: InventarioViewModel by viewModels()
    private val db = FirebaseFirestore.getInstance()

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
        sharedPreferences = requireActivity().getSharedPreferences("shared", Context.MODE_PRIVATE)
//        var art = Articulo(1, "Pollo", 20000, 4)
//        guardarArticulo(art)
        recycler()
        controllers()

        //guardar sesión: cuando se tenga hecho el logout, descomentar la función XD
//        saveLogin()

    }
    private fun controllers() {

        binding.addButton.setOnClickListener{
            findNavController().navigate(R.id.action_fragmentInventario_to_addProduct)

        }

    }



    fun recycler(){
        var listaArticulos = inventarioViewModel.listarArticulos()

        val recycler = binding.recyclerview
        recycler.layoutManager = LinearLayoutManager(context)
        db.collection("articulo").document().addSnapshotListener{value, error->
            error?.let{
                error(error)
            }
            value?.let{
//                val data = value.toObjects(Articulo::class.java)
                val adapter = RecyclerAdapter(listaArticulos)
                recycler.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        }
    }



    private fun saveLogin(){
        val bundle = requireActivity().intent.extras
        val email = bundle?.getString("email")
        sharedPreferences.edit().putString("email",email).apply()
    }

}