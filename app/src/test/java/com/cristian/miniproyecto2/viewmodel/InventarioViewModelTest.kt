package com.cristian.miniproyecto2.viewmodel

import android.content.Context
import com.cristian.miniproyecto2.model.Articulo
import com.cristian.miniproyecto2.repository.InventarioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class InventarioViewModelTest{
    private lateinit var inventarioViewModel: InventarioViewModel
    private lateinit var inventarioRepository: InventarioRepository

    @Mock
    private lateinit var mockContext: Context

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        inventarioRepository = Mockito.mock(InventarioRepository::class.java)
        inventarioViewModel = InventarioViewModel(inventarioRepository)
    }

    @Test
    fun `test method GuardarArticulo_Success`() {
        //given
        val articulo= Articulo(1, "Item1", 10.0, 5)

        //when
        `when`(inventarioRepository.guardarArticulo(articulo))
            .thenAnswer { invocation ->
                val inventoryArgument = invocation.getArgument<Articulo>(0)
                inventoryArgument
            }

        //then
        inventarioViewModel.guardarArticulo(articulo)

        verify(inventarioRepository).guardarArticulo(articulo)
    }

    @Test
    fun `test method ListarArticulos_Success`() {
        //given
        val articulos = listOf(
            Articulo(1, "Articulo1", 10.0, 5),
            Articulo(2, "Articulo2", 15.0, 8)
        )

        //when
        `when`(inventarioRepository.listarArticulos {}).thenAnswer {
            val callback = it.arguments[0] as (List<Articulo>) -> Unit
            callback.invoke(articulos)
        }

        //then
        inventarioViewModel.listarArticulos { result ->
            assert(result == articulos)
        }
    }

    @Test
    fun testEditarArticulo_Success() {
        //given
        val idArticulo = "1"
        val nombreArticulo = "NuevoArticulo"
        val precioArticulo = 15.0
        val cantidadArticulo = 10L

        //when
        inventarioViewModel.editarArticulo(idArticulo, nombreArticulo, precioArticulo, cantidadArticulo, mockContext)

        //then
        verify(inventarioRepository).editarArticulo(idArticulo, nombreArticulo, precioArticulo, cantidadArticulo, mockContext)
    }

    @Test
    fun `testEliminarArticulo_Success`() {
        // Given
        val idArticulo = "1"

        // When
        `when`(inventarioRepository.eliminarArticulo(idArticulo,mockContext))
            .thenAnswer { invocation ->
                val idArgument = invocation.getArgument<String>(0)
                idArgument
            }

        // Then
        inventarioViewModel.eliminarArticulo(idArticulo, mockContext)

        verify(inventarioRepository).eliminarArticulo(idArticulo, mockContext)
    }

}