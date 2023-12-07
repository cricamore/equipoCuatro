package com.cristian.miniproyecto2.data
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cristian.miniproyecto2.data.ProductoDao
import com.cristian.miniproyecto2.model.Producto
import com.cristian.miniproyecto2.utils.Constants.NAME_BD

@Database(entities = [Producto::class], version = 1)
abstract class ProductoDB : RoomDatabase() {

    abstract fun productoDao(): ProductoDao

    companion object{
        fun getDatabase(context: Context): ProductoDB {
            return Room.databaseBuilder(
                context.applicationContext,
                ProductoDB::class.java,
                NAME_BD
            ).build()
        }
    }
}