package com.example.app.auth


data class LoginResult(
    val ok: Boolean,
    val mensaje: String? = null,
    val usuario: Usuario? = null
)

data class Usuario(val correo: String,val nombre: String,val contrasena: String)

interface TestRepository {
    fun autenticar(correo: String, contrasena: String): LoginResult
}