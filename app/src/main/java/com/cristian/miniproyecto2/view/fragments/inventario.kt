package com.cristian.miniproyecto2.view.fragments

//import android.content.Intent.getIntent
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cristian.miniproyecto2.databinding.FragmentInventarioBinding
import com.cristian.miniproyecto2.model.Articulo
import com.cristian.miniproyecto2.view.RecyclerAdapter
import com.google.firebase.firestore.FirebaseFirestore

class inventario : Fragment() {
    private lateinit var binding: FragmentInventarioBinding
    private lateinit var sharedPreferences: SharedPreferences
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

        //guardar sesión: cuando se tenga hecho el logout, descomentar la función XD
        //saveLogin()

    }

    fun recycler(){
        var listaArticulos = listarArticulos()

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

    private fun guardarArticulo(articulo: Articulo){
        db.collection("articulo").document(articulo.id.toString()).set(
            hashMapOf(
                "id" to articulo.id,
                "name" to articulo.name,
                "price" to articulo.price,
                "quantity" to articulo.quantity
            )
        )
    }

    private fun listarArticulos(): MutableList<Articulo> {
        var articulos = mutableListOf<Articulo>()
        db.collection("articulo").get().addOnSuccessListener {
            for (document in it.documents) {
                val articulo = Articulo( // crea un objeto Articulo con los datos del documento
                    id = document.get("id") as Long,
                    name = document.get("name") as String,
                    price = document.get("price") as Long,
                    quantity = document.get("quantity") as Long
                )
                articulos.add(articulo) // añade el objeto Articulo a la lista articulos
            }
        }
        return articulos
    }

    private fun saveLogin(){
        val bundle = requireActivity().intent.extras
        val email = bundle?.getString("email")
        sharedPreferences.edit().putString("email",email).apply()
    }

}