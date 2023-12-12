package com.cristian.miniproyecto2.repository

import com.cristian.miniproyecto2.model.Articulo
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class InventarioRepository @Inject constructor(
) {
    private val db = FirebaseFirestore.getInstance()

    fun guardarArticulo(articulo: Articulo) {
        db.collection("articulo").document(articulo.id.toString()).set(
            hashMapOf(
                "id" to articulo.id,
                "name" to articulo.name,
                "price" to articulo.price,
                "quantity" to articulo.quantity
            )
        )
    }


    fun listarArticulos(): MutableList<Articulo> {
        var articulos = mutableListOf<Articulo>()
        var suma = 0.0
        db.collection("articulo").get().addOnSuccessListener {
            for (document in it.documents) {
                val articulo = Articulo( // crea un objeto Articulo con los datos del documento
                    id = document.get("id") as Long,
                    name = document.get("name") as String,
                    price = document.get("price") as Double,
                    quantity = document.get("quantity") as Long
                )
                articulos.add(articulo) // añade el objeto Articulo a la lista articulos
                suma += articulo.price * articulo.quantity
            }
        }
        return articulos
    }
}