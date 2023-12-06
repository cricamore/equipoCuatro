package com.cristian.miniproyecto2.fragments

import android.graphics.Typeface
import android.os.Bundle
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.cristian.miniproyecto2.R
import com.cristian.miniproyecto2.databinding.FragmentAuthBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class auth : Fragment() {
    private lateinit var binding: FragmentAuthBinding
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var loginButton: Button
    private lateinit var registrarText: TextView


    private var isPasswordVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.password.doOnTextChanged{ text,start,before,count ->
            if(text?.length?: 0 < 6){
                binding.passwordInputLayout.error = "Mínimo 6 dígitos"
            }
            else{
                binding.passwordInputLayout.error = null
            }

        }

        binding.passwordInputLayout.setEndIconOnClickListener {
            togglePasswordVisibility()
        }

        enableButton()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controllers()
    }
    private fun controllers() {
        binding.loginButton.setOnClickListener {
            findNavController().navigate(R.id.action_to_fragmentInventario)
        }
    }


    private fun enableButton(){
        emailEditText = binding.email
        passwordEditText = binding.password
        loginButton = binding.loginButton
        registrarText = binding.registerTextView

        loginButton.isEnabled = false
        registrarText.isEnabled = false

        registrarText.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
        loginButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))

        emailEditText.doOnTextChanged { email, _, _, _ ->
            updateButtonState(email, passwordEditText.text)
        }

        passwordEditText.doOnTextChanged { password, _, _, _ ->
            updateButtonState(emailEditText.text, password)
        }

    }

    private fun updateButtonState(email: CharSequence?, password: CharSequence?) {
        val isFieldsNotEmpty = email?.isNotEmpty() == true && password?.isNotEmpty() == true
        loginButton.isEnabled = isFieldsNotEmpty

        if (isFieldsNotEmpty) {
            loginButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            loginButton.setTypeface(null, Typeface.BOLD)
            registrarText.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            registrarText.setTypeface(null, Typeface.BOLD)
        } else {
            loginButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            loginButton.setTypeface(null, Typeface.NORMAL)
            registrarText.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            registrarText.setTypeface(null, Typeface.NORMAL)
        }
    }

    private fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible

        if (isPasswordVisible) {
            binding.password.transformationMethod = null
            binding.passwordInputLayout.endIconDrawable =
                ContextCompat.getDrawable(requireContext(), R.drawable.eye_close)
        } else {
            binding.password.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.passwordInputLayout.endIconDrawable =
                ContextCompat.getDrawable(requireContext(), R.drawable.eye_open)
        }
    }

}