package com.cristian.miniproyecto2.repository

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val firebaseAuth : FirebaseAuth
) {

    fun registerUser(email: String, pass:String, isRegisterComplete: (Boolean)->Unit){
        if(email.isNotEmpty() && pass.isNotEmpty()){
            firebaseAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        isRegisterComplete(true)
                    } else {
                        isRegisterComplete(false)
                    }
                }
        }else{
            isRegisterComplete(false)
        }
    }

    fun loginUser(email: String, pass: String, isLogin: (Boolean) -> Unit) {
        if (email.isNotEmpty() && pass.isNotEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        isLogin(true)
                    } else {
                        isLogin(false)
                    }
                }
        } else {
            isLogin(false)
        }
    }

    fun logout(){
        firebaseAuth.signOut()
    }
}