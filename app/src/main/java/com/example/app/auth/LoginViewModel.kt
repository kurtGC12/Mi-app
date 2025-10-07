package com.example.app.auth

class LoginViewModel(private val repo: TestRepository) {

    fun login (correo: String, password: String) : LoginResult{

        if (correo.isBlank()|| password.isBlank()){
            return LoginResult(false, "Campos vacios")
        }
    if (!correo.contains("@")){
        return LoginResult(false, "Correo invalido")
    }
    if (password.length < 4){
        return LoginResult(false, "Contraseña demasiado corta")
    }
    val resultado = repo.autenticar(correo, password)

    return if (resultado.ok){
        LoginResult(true,usuario = resultado.usuario)
    } else {
        resultado
    }

    }

}