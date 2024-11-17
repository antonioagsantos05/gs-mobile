package com.example.myapplication.model

data class CadastroRequest(
    val nmUsuario: String,  // Nome completo do usuário
    val login: String,      // Nome de login do usuário
    val password: String,   // Senha
    val nmEmail: String,    // Email
    val role: String        // Papel do usuário (ADMIN ou USER)
)
