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

    // Obter dados do usuário logado
    @GET("usuarios/me")
    fun getUsuarioLogado(): Call<UsuarioResponse>

    // Atualizar dados do usuário logado
    @PUT("usuarios/me")
    fun updateUsuario(@Body usuario: UsuarioRequest): Call<UsuarioResponse>

    // Deletar usuário logado
    @DELETE("usuarios/me")
    fun deleteUsuario(): Call<Void>
}
