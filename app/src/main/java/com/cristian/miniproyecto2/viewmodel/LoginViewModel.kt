package com.cristian.miniproyecto2.viewmodel

import androidx.lifecycle.ViewModel
import com.cristian.miniproyecto2.repository.LoginRepository
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {
    private val repository = LoginRepository()

    fun registerUser(email: String, pass: String, isRegister: (Boolean) -> Unit) {
        repository.registerUser(email, pass) { response ->
            isRegister(response)
        }
    }

    fun loginUser(email: String, pass: String, isLogin: (Boolean) -> Unit) {
        repository.loginUser(email, pass) { response ->
            isLogin(response)
        }
    }

    fun sesion(email: String?, isEnableView: (Boolean) -> Unit) {
        if (email != null) {
            isEnableView(true)
        } else {
            isEnableView(false)
        }
    }
}