package com.cristian.miniproyecto2.view

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import com.cristian.miniproyecto2.R
import com.cristian.miniproyecto2.databinding.ActivityLoginBinding
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var loginButton: Button
    private lateinit var registrarText: TextView

    private var isPasswordVisible = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.password.doOnTextChanged { text, _, _, _ ->
            if (text?.length ?: 0 > 0) {
                validatePasswordLength(text)
            } else {
                binding.passwordInputLayout.error = null
            }
        }

        enableButton()
        togglePasswordVisibility()

    }

    private fun validatePasswordLength(text: CharSequence?) {
        if (text?.length ?: 0 < 6) {
            binding.passwordInputLayout.error = "Mínimo 6 dígitos"
        } else {
            binding.passwordInputLayout.error = null
        }
    }

    private fun enableButton(){
        emailEditText = binding.email
        passwordEditText = binding.password
        loginButton = binding.loginButton
        registrarText = binding.registerTextView

        loginButton.isEnabled = false
        registrarText.isEnabled = false

        registrarText.setTextColor(ContextCompat.getColor(this, R.color.gray))
        loginButton.setTextColor(ContextCompat.getColor(this, R.color.gray))

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
            loginButton.setTextColor(ContextCompat.getColor(this, R.color.white))
            loginButton.setTypeface(null, Typeface.BOLD)
            registrarText.setTextColor(ContextCompat.getColor(this, R.color.white))
            registrarText.setTypeface(null, Typeface.BOLD)
        } else {
            loginButton.setTextColor(ContextCompat.getColor(this, R.color.gray))
            loginButton.setTypeface(null, Typeface.NORMAL)
            registrarText.setTextColor(ContextCompat.getColor(this, R.color.gray))
            registrarText.setTypeface(null, Typeface.NORMAL)
        }
    }

    private fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible

        if (isPasswordVisible) {
            binding.password.transformationMethod = null
            binding.passwordInputLayout.endIconDrawable =
                ContextCompat.getDrawable(this, R.drawable.eye_close)
        } else {
            binding.password.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.passwordInputLayout.endIconDrawable =
                ContextCompat.getDrawable(this, R.drawable.eye_open)
        }
    }
}