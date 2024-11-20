package com.example.myapplication.model

data class UsuarioRequest(
    val nmUsuario: String,  // Nome completo do usuário
    val nmLogin: String,    // Nome de login do usuário
    val nmSenha: String,    // Nova senha do usuário
    val nmEmail: String     // Email do usuário
)
