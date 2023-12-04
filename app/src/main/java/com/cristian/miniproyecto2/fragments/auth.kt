package com.cristian.miniproyecto2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cristian.miniproyecto2.R
import com.cristian.miniproyecto2.databinding.FragmentAuthBinding

class auth : Fragment() {
    private lateinit var binding: FragmentAuthBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }
}