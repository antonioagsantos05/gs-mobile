package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.network.RetrofitInstance
import com.example.myapplication.network.TokenUtils.extrairIdDoUsuario
import com.example.myapplication.model.UsuarioResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerfilFragment : Fragment() {

    private val TAG = "PerfilFragment"

    private lateinit var tvNome: TextView
    private lateinit var tvUsuario: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvSenha: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_perfil, container, false)

        tvNome = view.findViewById(R.id.tvNome)
        tvUsuario = view.findViewById(R.id.tvUsuario)
        tvEmail = view.findViewById(R.id.tvEmail)
        tvSenha = view.findViewById(R.id.tvSenha)

        buscarDadosUsuario()

        return view
    }

    private fun buscarDadosUsuario() {
        val sharedPreferences = requireContext().getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("jwt_token", null)
        val userId = extrairIdDoUsuario(requireContext()) // Extrai o ID do token

        if (token.isNullOrEmpty() || userId == -1L) {
            Log.e(TAG, "Token ou ID do usuário não encontrados.")
            Toast.makeText(context, "Faça login novamente.", Toast.LENGTH_SHORT).show()
            realizarLogout()
            return
        }

        Log.d(TAG, "Token encontrado: $token")
        Log.d(TAG, "ID do usuário extraído: $userId")

        RetrofitInstance.api.getUsuarioLogado(authHeader = "Bearer $token", id = userId).enqueue(object : Callback<UsuarioResponse> {
            override fun onResponse(call: Call<UsuarioResponse>, response: Response<UsuarioResponse>) {
                if (response.isSuccessful) {
                    val usuario = response.body()
                    if (usuario != null) {
                        tvNome.text = usuario.nmUsuario
                        tvUsuario.text = usuario.nmLogin
                        tvEmail.text = usuario.nmEmail
                        tvSenha.text = "********" // Exibe a senha mascarada
                        Log.d(TAG, "Dados do usuário carregados com sucesso.")
                    } else {
                        Log.e(TAG, "Resposta vazia do servidor.")
                        Toast.makeText(context, "Erro ao carregar dados do usuário.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e(TAG, "Erro na resposta: ${response.code()} - ${response.errorBody()?.string()}")
                    Toast.makeText(context, "Erro ao carregar dados do usuário.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UsuarioResponse>, t: Throwable) {
                Log.e(TAG, "Erro de conexão: ${t.message}")
                Toast.makeText(context, "Erro de conexão. Verifique sua internet.", Toast.LENGTH_SHORT).show()
            }
        })
    }



    private fun realizarLogout() {
        val sharedPreferences = requireContext().getSharedPreferences("user_data", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
        activity?.finish()
    }
}
