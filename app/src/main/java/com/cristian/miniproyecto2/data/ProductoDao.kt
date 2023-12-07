package com.cristian.miniproyecto2.data
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.cristian.miniproyecto2.model.Producto

@Dao
interface ProductoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProducto(producto: Producto)

    @Query("SELECT * FROM Producto")
    suspend fun getListProducto(): MutableList<Producto>

    @Delete
    suspend fun deleteProducto(producto: Producto)

    @Update
    suspend fun updateProducto(producto: Producto)
}