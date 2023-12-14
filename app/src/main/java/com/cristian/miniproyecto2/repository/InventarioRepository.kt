package com.cristian.miniproyecto2.repository

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
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

    fun editarArticulo(idArticulo: String, nombreArticulo: String, precioArticulo: Double, cantidadArticulo: Long, contexto: Context){
        val productoRef = db.collection("articulo").document(idArticulo.toString())

        if (nombreArticulo.isNotEmpty() && precioArticulo != null && cantidadArticulo != null) {
            val nuevosDatos = hashMapOf(
                "name" to nombreArticulo,
                "price" to precioArticulo,
                "quantity" to cantidadArticulo
            )

            productoRef
                .update(nuevosDatos as Map<String, Any>)
                .addOnSuccessListener {
                    // Actualización exitosa
                    Toast.makeText(contexto, "Artículo actualizado", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    // Error al actualizar
                    Toast.makeText(contexto, "Error al actualizar el documento", Toast.LENGTH_SHORT).show()
                }
        } else {
            // Manejar el caso de campos vacíos o valores no numéricos
            Toast.makeText(contexto, "Ingrese valores válidos", Toast.LENGTH_SHORT).show()
        }
    }

    fun eliminarArticulo(idArticulo: String, contexto: Context){
        val docRef = db.collection("articulo").document(idArticulo)

        // Elimina el documento de la colección
        docRef.delete()
            .addOnSuccessListener {
                // Eliminación exitosa
                Toast.makeText(contexto, "Artículo eliminado", Toast.LENGTH_SHORT).show()
                // Aquí puedes realizar otras acciones después de eliminar el artículo
            }
            .addOnFailureListener { e ->
                // Error al eliminar el documento
                Toast.makeText(contexto, "Error al eliminar: $e", Toast.LENGTH_SHORT).show()
            }
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