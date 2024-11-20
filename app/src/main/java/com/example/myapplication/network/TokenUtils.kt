package com.example.myapplication.network

import android.content.Context
import android.util.Log
import com.auth0.android.jwt.JWT

object TokenUtils {
    fun extrairIdDoUsuario(context: Context): Long {
        val sharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("jwt_token", null)

        if (token.isNullOrEmpty()) {
            Log.e("TokenError", "Token JWT não encontrado no SharedPreferences.")
            return -1L
        }

        return try {
            val jwt = JWT(token)
            val userId = jwt.getClaim("idUsuario").asLong() ?: -1L
            Log.d("TokenDecoded", "ID do usuário extraído do token: $userId")
            userId
        } catch (e: Exception) {
            Log.e("TokenError", "Erro ao decodificar o token JWT: ${e.message}")
            -1L
        }
    }
}
