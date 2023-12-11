package com.cristian.miniproyecto2.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.cristian.miniproyecto2.R
import com.cristian.miniproyecto2.databinding.FragmentProductDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentProductDetail : Fragment() {

    lateinit var binding : FragmentProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

}