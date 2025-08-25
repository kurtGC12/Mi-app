package com.example.app.untils

data class ValidationResult(
    val success: Boolean,
    val errors: List<String> = emptyList()
)

object Registros {
    fun validarRegistro(nombre: String, correo: String, password: String): ValidationResult {

        val errores = mutableListOf<String>()
        if (nombre.isBlank()) {
            errores.add("El nombre no puede estar vacío")
        }
        if (correo.isBlank()) {
            errores.add("El correo no puede estar vacío")
        }

        if (password.isBlank()) {
            errores.add("La contraseña no puede estar vacía")
        }
        else if ( password.length < 6 ) {
            errores.add("La contraseña debe tener al menos 6 caracteres")
        }
     return if (errores.isEmpty()) ValidationResult(true) else ValidationResult(false ,errores)
    }
val registros = mutableListOf<Map<String, String>>()

    //completar validacion
   // val validacion = validarRegistro("nombre", "correo", "password")
    //if (!validacion.success )  return validacion

    fun guardarRegistro(nombre: String, correo: String, password: String): ValidationResult {
        val nuevoRegistro = mapOf(
            "nombre" to nombre,
            "correo" to correo,
            "password" to password
        )
        registros.add(nuevoRegistro)

        return ValidationResult(success = true)
    }
}




