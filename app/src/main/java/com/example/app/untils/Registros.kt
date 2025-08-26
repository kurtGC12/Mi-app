package com.example.app.untils

import android.util.Patterns


data class ValidationResult(
    val success: Boolean,
    val errors: List<String> = emptyList()
)

object Registros {
    fun validarRegistro(nombre: String, correo: String, password: String, terminos: Boolean): ValidationResult {

        val errores = mutableListOf<String>()
        if (nombre.isBlank()) {
            errores.add("El nombre no puede estar vacío")
        }
        if (correo.isBlank()) {
            errores.add("El correo no puede estar vacío")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            errores.add("El correo no tiene un formato válido")
        } else if (registros.any { it["correo"] == correo }) {
            errores.add("El correo ya está registrado")
        }

        if (password.isBlank()) {
            errores.add("La contraseña no puede estar vacía")
        }
        else if ( password.length < 6 ) {
            errores.add("La contraseña debe tener al menos 6 caracteres")
        }
        if (!terminos) {
            errores.add("Debes aceptar los términos y condiciones")
        }
     return if (errores.isEmpty()) ValidationResult(true) else ValidationResult(false ,errores)
    }
val registros = mutableListOf<Map<String, Any>>()

    fun guardarRegistro(nombre: String, correo: String, password: String, terminos: Boolean): ValidationResult {
        val validacion = validarRegistro("nombre", "correo", "password", terminos)
        if (!validacion.success) return validacion

        val nuevoRegistro = mapOf(
            "nombre" to nombre,
            "correo" to correo,
            "password" to password,
            "terminos" to terminos
        )
        registros.add(nuevoRegistro)

        return ValidationResult(success = true)
    }
    fun autenticar(correo: String, password: String): ValidationResult {
        val errores = mutableListOf<String>()
        if (correo.isBlank()) errores.add("Ingrese su correo")
        if (password.isBlank()) errores.add("Ingrese su contraseña")
        if (errores.isNotEmpty()) return ValidationResult(false, errores)

        val ok = registros.any {
            (it["correo"] as? String)?.equals(correo.trim(), ignoreCase = true) == true &&
                    (it["password"] as? String) == password
        }

        return if (ok) {
            ValidationResult(true)
        } else {
            ValidationResult(false, listOf("Correo o contraseña incorrectos"))
        }
}
}




