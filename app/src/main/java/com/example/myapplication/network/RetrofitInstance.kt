package com.example.myapplication.network

import android.content.Context
import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://api-java-devops.azurewebsites.net/"

    private fun createClient(context: Context): OkHttpClient {
        val sharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("jwt_token", null)

        Log.d("Retrofit", "Token JWT recuperado: $token") // Log para verificar o token

        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                if (token != null) {
                    requestBuilder.addHeader("Authorization", "Bearer $token")
                    Log.d("Retrofit", "Token JWT adicionado ao cabeçalho: Bearer $token") // Logando o token
                }
                chain.proceed(requestBuilder.build())
            }
            .build()
    }

    fun createApi(context: Context): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }

    lateinit var api: ApiService

    fun initialize(context: Context) {
        api = createApi(context)
    }
}
