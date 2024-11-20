package com.example.myapplication.model

data class LoginResponse(
    val token: String,   // Token JWT retornado pela API
    val id: Long         // ID do usuário autenticado
)
