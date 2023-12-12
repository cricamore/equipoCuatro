package com.cristian.miniproyecto2.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import com.cristian.miniproyecto2.R
import com.cristian.miniproyecto2.databinding.ActivityLoginBinding
import com.cristian.miniproyecto2.viewmodel.LoginViewModel
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var loginButton: Button
    private lateinit var registrarText: TextView

    private var isPasswordVisible = false
    private var launchedFromWidget: Boolean = false

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        launchedFromWidget = intent.getBooleanExtra("launchedFromWidget", false)

        sharedPreferences = getSharedPreferences("shared", Context.MODE_PRIVATE)

        binding.password.doOnTextChanged { text, _, _, _ ->
            if (text?.length ?: 0 > 0) {
                validatePasswordLength(text)
            } else {
                binding.passwordInputLayout.error = null
            }
        }

        saveSession()
        enableButton()

        binding.passwordInputLayout.setEndIconOnClickListener {
            togglePasswordVisibility()
        }

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
        registrarText.isEnabled = isFieldsNotEmpty

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

        binding.registerTextView.setOnClickListener {
            register()
        }

        binding.loginButton.setOnClickListener {
            login()
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

    private fun register(){
        val email = binding.email.text.toString()
        val pass = binding.password.text.toString()
        loginViewModel.registerUser(email,pass) { isRegister ->
            if (isRegister) {
                navInventory(email)
            } else {
                Toast.makeText(this, "Error en el registro", Toast.LENGTH_SHORT).show()
            }

        }
    }
    private fun login(){
        val email = binding.email.text.toString()
        val pass = binding.password.text.toString()

        loginViewModel.loginUser(email,pass){ isLogin ->
            if (isLogin){
                if(launchedFromWidget){
                    sharedPreferences.edit().putString("email", email).apply()
                    finishAffinity()
                }else{
                    navInventory(email)
                }
            }else {
                Toast.makeText(this, "Login incorrecto", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navInventory(email: String?){
        val intent = Intent (this, InventarioActivity::class.java).apply {
            putExtra("email",email)
        }
        startActivity(intent)
        finish()
    }

    private fun saveSession(){
        val email = sharedPreferences.getString("email",null)
        loginViewModel.sesion(email){isEnableView ->
            if(isEnableView){
                binding.loginLayout.visibility = View.INVISIBLE
                navInventory(email)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.loginLayout.visibility = View.VISIBLE
    }


}