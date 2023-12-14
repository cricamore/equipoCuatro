
package com.cristian.miniproyecto2.viewmodel

import com.cristian.miniproyecto2.repository.LoginRepository
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.nullable
import org.mockito.Captor
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class LoginViewModelTest{
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginRepository: LoginRepository
    @Before
    fun setup(){
        loginRepository = mock(LoginRepository::class.java)
        loginViewModel = LoginViewModel(loginRepository)
    }

    @Test
    fun `test method registerUserSuccess`() {
        //given
        val email = "test@example.com"
        val password = "123456"
        val expectedResult = true

        //when
        `when`(loginRepository.registerUser(email, password) {}).thenAnswer {
            val callback = it.arguments[2] as (Boolean) -> Unit
            callback.invoke(expectedResult)
        }

        //then
        loginViewModel.registerUser(email, password) { result ->
            assert(result)
        }
    }

    @Test
    fun `test method loginUserSuccess`() {
        //given
        val email = "test@example.com"
        val password = "123456"
        val expectedResult = true

        //when
        `when`(loginRepository.loginUser(email, password) {}).thenAnswer {
            val callback = it.arguments[2] as (Boolean) -> Unit
            callback.invoke(expectedResult)
        }

        //then
        loginViewModel.loginUser(email, password) { result ->
            assert(result)
        }

    }

    @Test
    fun `test method Sesion_EnableView`() {
        //given
        val email = "test@example.com"

        //when
        var result: Boolean? = null
        loginViewModel.sesion(email) {
            result = it
        }

        //then
        assert(result == true)
    }

}