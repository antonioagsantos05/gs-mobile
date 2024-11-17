package com.example.myapplication.model

data class UsuarioResponse(
    val id: Long,           // ID do usuário
    val nmUsuario: String,  // Nome do usuário
    val login: String,      // Login do usuário
    val nmEmail: String,    // Email do usuário
    val role: String        // Papel do usuário (ADMIN ou USER)
)
