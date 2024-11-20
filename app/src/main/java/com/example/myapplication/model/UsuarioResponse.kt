package com.example.myapplication.model

data class UsuarioResponse(
    val idUsuario: Long,     // ID do usuário
    val nmUsuario: String,   // Nome completo do usuário
    val nmLogin: String,     // Nome de login do usuário
    val nmSenha: String,     // Senha do usuário (criptografada)
    val nmEmail: String      // Email do usuário
)
