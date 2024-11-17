package com.example.myapplication.network

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://java-gs2-api-b0crgfdaf5dcftcy.brazilsouth-01.azurewebsites.net/"

    // Client HTTP configurado com autenticação JWT
    private fun createClient(context: Context): OkHttpClient {
        val sharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("jwt_token", null)

        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                if (token != null) {
                    requestBuilder.addHeader("Authorization", "Bearer $token")
                }
                chain.proceed(requestBuilder.build())
            }
            .build()
    }

    // Retrofit configurado com o client e base URL
    fun createApi(context: Context): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }

    // Propriedade API configurada para facilitar acesso
    lateinit var api: ApiService

    // Função de inicialização, chamada no início da aplicação
    fun initialize(context: Context) {
        api = createApi(context)
    }
}
