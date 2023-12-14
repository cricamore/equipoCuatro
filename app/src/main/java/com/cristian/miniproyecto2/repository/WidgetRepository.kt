package com.cristian.miniproyecto2.repository

import com.cristian.miniproyecto2.model.Articulo
import com.google.firebase.firestore.FirebaseFirestore

class WidgetRepository {
    private val db = FirebaseFirestore.getInstance()
    fun sumaPrecios(callback: (Double) -> Unit) {
        var suma = 0.0
        db.collection("articulo").get().addOnSuccessListener { result ->
            for (document in result.documents) {
                val articulo = Articulo(
                    id = document.get("id") as Long,
                    name = document.get("name") as String,
                    price = document.get("price") as Double,
                    quantity = document.get("quantity") as Long
                )
                suma += articulo.price * articulo.quantity.toDouble()
            }
            callback(suma)
        }
    }
}