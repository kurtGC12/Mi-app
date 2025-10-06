package com.example.app.untils

import android.content.Context
import android.util.Patterns
import com.example.app.data.User
import com.example.app.data.UserDao

data class ValidationResult(
    val success: Boolean,
    val errors: List<String> = emptyList()
)

object RegistrosHelper {


    fun validarRegistro(
        context: Context,
        nombre: String,
        correo: String,
        password: String,
        terminos: Boolean
    ): ValidationResult {
        val errores = mutableListOf<String>()

        if (nombre.isBlank()) errores.add("El nombre no puede estar vacío")

        if (correo.isBlank()) {
            errores.add("El correo no puede estar vacío")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            errores.add("El correo no tiene un formato válido")
        } else if (emailExiste(context, correo)) {
            errores.add("El correo ya está registrado")
        }

        if (password.isBlank()) {
            errores.add("La contraseña no puede estar vacía")
        } else if (password.length < 6) {
            errores.add("La contraseña debe tener al menos 6 caracteres")
        }

        if (!terminos) errores.add("Debes aceptar los términos y condiciones")

        return if (errores.isEmpty()) ValidationResult(true) else ValidationResult(false, errores)
    }


    fun guardarRegistro(
        context: Context,
        nombre: String,
        correo: String,
        password: String,
        terminos: Boolean
    ): ValidationResult {
        val validacion = validarRegistro(context, nombre, correo, password, terminos)
        if (!validacion.success) return validacion

        val dao = UserDao(context)
        val nuevoUsuario = User(
            id = 0,
            nombre = nombre,
            correo = correo,
            contrasena = password
        )

        val ok = dao.insert(nuevoUsuario)
        return if (ok) ValidationResult(true) else ValidationResult(false, listOf("Error al guardar en la base de datos"))
    }

    fun autenticar(
        context: Context,
        correo: String,
        password: String
    ): ValidationResult {
        val errores = mutableListOf<String>()
        if (correo.isBlank()) errores.add("Ingrese su correo")
        if (password.isBlank()) errores.add("Ingrese su contraseña")
        if (errores.isNotEmpty()) return ValidationResult(false, errores)

        val dao = UserDao(context)
        val encontrado = dao.getAll().any {
            it.correo.equals(correo.trim(), ignoreCase = true) && it.contrasena == password
        }

        return if (encontrado) ValidationResult(true)
        else ValidationResult(false, listOf("Correo o contraseña incorrectos"))
    }

    private fun emailExiste(context: Context, correo: String): Boolean {
        val dao = UserDao(context)
        return dao.getAll().any { it.correo.equals(correo.trim(), ignoreCase = true) }
    }
}
