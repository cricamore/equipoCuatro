package com.cristian.miniproyecto2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.cristian.miniproyecto2.R
import com.cristian.miniproyecto2.databinding.ActivityInventarioBinding

class InventarioActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInventarioBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_inventario)
    }
}