package com.example.myapplication.network

import com.example.myapplication.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    // Registro de Usuários
    @POST("auth/register")
    fun registrar(@Body cadastroRequest: CadastroRequest): Call<Void> // Void porque a API retorna apenas 200 OK

    // Login de Usuários
    @POST("auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse> // Retorna o token JWT

    // Obter dados do usuário logado pelo ID
    @GET("usuarios/{id}")
    fun getUsuarioLogado(
        @Header("Authorization") authHeader: String,
        @Path("id") id: Long
    ): Call<UsuarioResponse>

    // Atualizar dados do usuário
    @PUT("usuarios/{id}")
    fun updateUsuario(
        @Header("Authorization") authHeader: String,
        @Path("id") id: Long,
        @Body usuarioRequest: UsuarioRequest
    ): Call<UsuarioResponse>

    // Deletar usuário
    @DELETE("usuarios/{id}")
    fun deleteUsuario(
        @Header("Authorization") authHeader: String,
        @Path("id") id: Long
    ): Call<Void>

}
