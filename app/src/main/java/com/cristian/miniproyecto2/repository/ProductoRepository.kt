package com.cristian.miniproyecto2.repository
import android.content.Context
import com.cristian.miniproyecto2.data.ProductoDB
import com.cristian.miniproyecto2.data.ProductoDao
import com.cristian.miniproyecto2.model.Producto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductoRepository(val context: Context){
    private var productoDao: ProductoDao = ProductoDB.getDatabase(context).productoDao()
    //private var apiService: ApiService = ApiUtils.getApiService()

    suspend fun saveProducto(producto: Producto){
        withContext(Dispatchers.IO){
            productoDao.saveProducto(producto)
        }
    }

    suspend fun getListProducto():MutableList<Producto>{
        productoDao.saveProducto(Producto(id = 0, name = "Arracacha", price = 10000, quantity = 1))
        productoDao.saveProducto(Producto(id = 0, name = "Perro", price = 10000, quantity = 1))
        productoDao.saveProducto(Producto(id = 0, name = "Gato", price = 10000, quantity = 1))
        productoDao.saveProducto(Producto(id = 0, name = "Oveja", price = 10000, quantity = 1))
        productoDao.saveProducto(Producto(id = 0, name = "Pollo", price = 10000, quantity = 1))
        productoDao.saveProducto(Producto(id = 0, name = "Lechuga", price = 10000, quantity = 1))
        productoDao.saveProducto(Producto(id = 0, name = "Galleta", price = 10000, quantity = 1))
        productoDao.saveProducto(Producto(id = 0, name = "Trufa", price = 10000, quantity = 1))
        productoDao.saveProducto(Producto(id = 0, name = "Zapato", price = 10000, quantity = 1))
        productoDao.saveProducto(Producto(id = 0, name = "Pantalon", price = 10000, quantity = 1))
        return withContext(Dispatchers.IO){
            productoDao.getListProducto()
        }
    }

    suspend fun deleteProducto(inventory: Producto){
        withContext(Dispatchers.IO){
            productoDao.deleteProducto(inventory)
        }
    }

    suspend fun updateProducto(inventory: Producto){
        withContext(Dispatchers.IO){
            productoDao.updateProducto(inventory)
        }
    }

}