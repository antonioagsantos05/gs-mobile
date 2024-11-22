package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.network.RetrofitInstance
import com.example.myapplication.network.TokenUtils.extrairIdDoUsuario
import com.example.myapplication.model.UsuarioRequest
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
    private lateinit var btnLogout: Button
    private lateinit var btnEditar: Button
    private lateinit var btnDeletar: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_perfil, container, false)

        tvNome = view.findViewById(R.id.tvNome)
        tvUsuario = view.findViewById(R.id.tvUsuario)
        tvEmail = view.findViewById(R.id.tvEmail)
        tvSenha = view.findViewById(R.id.tvSenha)
        btnLogout = view.findViewById(R.id.btn_logout)
        btnEditar = view.findViewById(R.id.btn_editar)
        btnDeletar = view.findViewById(R.id.btn_deletar)

        buscarDadosUsuario()

        btnLogout.setOnClickListener {
            realizarLogout()
        }

        btnEditar.setOnClickListener {
            abrirEditarPopup()
        }

        btnDeletar.setOnClickListener {
            abrirDeletarPopup()
        }

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

        Toast.makeText(context, "Logout realizado com sucesso.", Toast.LENGTH_SHORT).show()

        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

        activity?.finish()
    }

    private fun atualizarDadosUsuario(nome: String?, usuario: String?, email: String?, senha: String?) {
        val sharedPreferences = requireContext().getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("jwt_token", null)
        val userId = extrairIdDoUsuario(requireContext()) // Extrai o ID do token

        if (token.isNullOrEmpty() || userId == -1L) {
            Toast.makeText(requireContext(), "Erro ao autenticar. Faça login novamente.", Toast.LENGTH_SHORT).show()
            realizarLogout()
            return
        }

        val usuarioRequest = UsuarioRequest(
            nmUsuario = nome ?: tvNome.text.toString(),
            nmLogin = usuario ?: tvUsuario.text.toString(),
            nmEmail = email ?: tvEmail.text.toString(),
            nmSenha = senha ?: "********"
        )

        RetrofitInstance.api.updateUsuario("Bearer $token", userId, usuarioRequest)
            .enqueue(object : Callback<UsuarioResponse> {
                override fun onResponse(call: Call<UsuarioResponse>, response: Response<UsuarioResponse>) {
                    if (response.isSuccessful) {
                        val usuarioAtualizado = response.body()
                        if (usuarioAtualizado != null) {
                            // Atualiza os campos no Fragment
                            tvNome.text = usuarioAtualizado.nmUsuario
                            tvUsuario.text = usuarioAtualizado.nmLogin
                            tvEmail.text = usuarioAtualizado.nmEmail
                            Toast.makeText(requireContext(), "Dados atualizados com sucesso!", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(requireContext(), "Erro ao atualizar os dados.", Toast.LENGTH_SHORT).show()
                        Log.e(TAG, "Erro na API: ${response.code()} - ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<UsuarioResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "Erro ao conectar ao servidor.", Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "Erro de conexão: ${t.message}")
                }
            })
    }


    private fun abrirEditarPopup() {
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        val inflater = LayoutInflater.from(requireContext())
        val popupView = inflater.inflate(R.layout.popup_editar_perfil, null)

        val inputNome = popupView.findViewById<EditText>(R.id.inputNome)
        val inputUsuario = popupView.findViewById<EditText>(R.id.inputUsuario)
        val inputEmail = popupView.findViewById<EditText>(R.id.inputEmail)
        val inputSenha = popupView.findViewById<EditText>(R.id.inputSenha)

        // Preenche os campos com os dados existentes
        inputNome.setText(tvNome.text)
        inputUsuario.setText(tvUsuario.text)
        inputEmail.setText(tvEmail.text)

        builder.setView(popupView)
            .setTitle("Editar Perfil")
            .setPositiveButton("Salvar") { _, _ ->

                val novoNome = if (inputNome.text.toString().isBlank()) null else inputNome.text.toString()
                val novoUsuario = if (inputUsuario.text.toString().isBlank()) null else inputUsuario.text.toString()
                val novoEmail = if (inputEmail.text.toString().isBlank()) null else inputEmail.text.toString()
                val novaSenha = if (inputSenha.text.toString().isBlank()) null else inputSenha.text.toString()

                if (novoNome == null && novoUsuario == null && novoEmail == null && novaSenha == null) {
                    Toast.makeText(requireContext(), "Nenhum campo alterado.", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                atualizarDadosUsuario(novoNome, novoUsuario, novoEmail, novaSenha)
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }

        builder.create().show()
    }

    private fun deletarUsuario() {
        val sharedPreferences = requireContext().getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("jwt_token", null)
        val userId = extrairIdDoUsuario(requireContext()) // Extrai o ID do token

        if (token.isNullOrEmpty() || userId == -1L) {
            Toast.makeText(requireContext(), "Erro ao autenticar. Faça login novamente.", Toast.LENGTH_SHORT).show()
            realizarLogout()
            return
        }

        RetrofitInstance.api.deleteUsuario("Bearer $token", userId)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(), "Conta excluída com sucesso!", Toast.LENGTH_SHORT).show()
                        realizarLogout()
                    } else {
                        Toast.makeText(requireContext(), "Erro ao excluir a conta.", Toast.LENGTH_SHORT).show()
                        Log.e(TAG, "Erro na API: ${response.code()} - ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(requireContext(), "Erro ao conectar ao servidor.", Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "Erro de conexão: ${t.message}")
                }
            })
    }


    private fun abrirDeletarPopup() {
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        val inflater = LayoutInflater.from(requireContext())
        val popupView = inflater.inflate(R.layout.popup_excluir, null)

        // Referências aos botões do popup
        val btnConfirmar = popupView.findViewById<Button>(R.id.btnConfirmarExcluir)
        val btnCancelar = popupView.findViewById<Button>(R.id.btnCancelarExcluir)

        builder.setView(popupView)
        val dialog = builder.create()

        btnConfirmar.setOnClickListener {
            dialog.dismiss()
            deletarUsuario()
        }

        btnCancelar.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

}
