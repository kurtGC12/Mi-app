package com.example.app

import com.example.app.auth.LoginResult
import com.example.app.auth.TestRepository
import com.example.app.auth.LoginViewModel
import com.example.app.auth.Usuario
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.*

class LoginViewModelTest {

    //repositorio  mockeado para no depender de la base de datos
    private val repo = mock <TestRepository>()
    private val  viewModel = LoginViewModel(repo)

    // se ejecuta el login con correo y contraseña vacíos
    @Test
    fun `campos vacios `() {
        val resultado = viewModel.login("", "")

        assertFalse(resultado.ok)
        assertEquals("Campos vacios", resultado.mensaje)

        verifyNoInteractions(repo)
    }

    @Test
    fun `correo invalido`() {
        //Ejecutamos con formato de correo inválido (sin @).
        val resultado = viewModel.login("kurtgcgmail.cl", "1234")

        assertFalse(resultado.ok)
        assertEquals("Correo invalido", resultado.mensaje)

        verifyNoInteractions(repo)

    }
    // se ejecuta el login con correo y contraseña corta
    @Test
    fun `contraseña demasiado corta`() {
        val resultado = viewModel.login("kurtgc@gmail.cl", "123")
        assertFalse(resultado.ok)
        assertEquals("Contraseña demasiado corta", resultado.mensaje)

        verifyNoInteractions(repo)

    }
    // se ejecuta el login con correo y contraseña correctos
    @Test
    fun `login exitoso`() {

        val user = Usuario(correo = "kurtg@gmail.cl", nombre = "Kurt", contrasena = "123456")
        `when`(repo.autenticar("kurtgc@gmail.cl", "123456")).thenReturn(
            LoginResult(true, usuario = user)
        )

        val resultado = viewModel.login("kurtgc@gmail.cl", "123456")

        assertTrue(resultado.ok)
        assertNotNull(resultado.usuario)
        assertEquals("kurtgc@gmail.cl", resultado.usuario?.correo)
        assertEquals("Kurt", resultado.usuario?.nombre)
        assertEquals("123456", resultado.usuario?.contrasena)


        verify(repo).autenticar("kurtgc@gmail.cl", "123456")
    }
}