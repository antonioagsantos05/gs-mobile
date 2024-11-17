package com.example.myapplication.model

data class UsuarioRequest(
    val nmUsuario: String,  // Nome do usuário
    val login: String,      // Login do usuário
    val password: String,   // Senha do usuário
    val nmEmail: String,    // Email do usuário
)