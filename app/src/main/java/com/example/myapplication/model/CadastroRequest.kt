package com.example.myapplication.model

data class CadastroRequest(
    val nmUsuario: String,
    val login: String,
    val password: String,
    val nmEmail: String,
    val role: String
)
