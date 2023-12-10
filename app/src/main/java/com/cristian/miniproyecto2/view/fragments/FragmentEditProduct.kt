package com.cristian.miniproyecto2.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cristian.miniproyecto2.R
import com.cristian.miniproyecto2.databinding.FragmentEditProductBinding

class FragmentEditProduct : Fragment() {
    lateinit var binding : FragmentEditProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditProductBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

}