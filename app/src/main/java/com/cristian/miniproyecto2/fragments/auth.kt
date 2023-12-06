package com.cristian.miniproyecto2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.cristian.miniproyecto2.R
import com.cristian.miniproyecto2.databinding.FragmentAuthBinding
import com.google.android.material.textfield.TextInputLayout

class auth : Fragment() {
    private lateinit var binding: FragmentAuthBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.password.doOnTextChanged{ text,start,before,count ->
            if(text?.length?: 0 < 6){
                binding.password.error = "Mínimo 6 dígitos"
                binding.passwordInputLayout.endIconMode = TextInputLayout.END_ICON_NONE
                binding.passwordInputLayout.boxStrokeColor = ContextCompat.getColor(requireContext(), android.R.color.holo_red_dark)
                binding.passwordInputLayout.hintTextColor = ContextCompat.getColorStateList(requireContext(), android.R.color.holo_red_dark)

            }
            else{
                binding.password.error = null
                binding.passwordInputLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
                binding.passwordInputLayout.boxStrokeColor = ContextCompat.getColor(requireContext(), R.color.white)
                binding.passwordInputLayout.hintTextColor = ContextCompat.getColorStateList(requireContext(), R.color.white)
            }

        }

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controllers()
    }
    fun controllers(){
        binding.loginButton.setOnClickListener{
            findNavController().navigate(R.id.action_to_fragmentInventario)
        }

    }
}