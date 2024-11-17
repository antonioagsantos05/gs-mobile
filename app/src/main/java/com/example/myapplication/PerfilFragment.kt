package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.myapplication.model.UsuarioRequest
import com.example.myapplication.model.UsuarioResponse
import com.example.myapplication.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerfilFragment : Fragment() {

    private lateinit var nomeTextView: TextView
    private lateinit var usuarioTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var senhaTextView: TextView
    private lateinit var sharedPreferences: SharedPreferences
    private val tag = "PerfilFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_perfil, container, false)

        // Inicializa as Views
        nomeTextView = view.findViewById(R.id.tvNome)
        usuarioTextView = view.findViewById(R.id.tvUsuario)
        emailTextView = view.findViewById(R.id.tvEmail)
        senhaTextView = view.findViewById(R.id.tvSenha)

        sharedPreferences = requireContext().getSharedPreferences("user_data", Context.MODE_PRIVATE)

        // Busca os dados do usuário usando RetrofitInstance.api
        buscarDadosUsuario()

        // Configuração do botão de logout
        view.findViewById<Button>(R.id.btn_logout).setOnClickListener {
            sharedPreferences.edit().clear().apply()
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }

        // Configuração do botão de editar
        view.findViewById<Button>(R.id.btn_edit).setOnClickListener {
            mostrarPopupEdicao()
        }

        // Configuração do botão de deletar conta
        view.findViewById<Button>(R.id.btn_delete).setOnClickListener {
            confirmarDelecao()
        }

        return view
    }

    private fun buscarDadosUsuario() {
        RetrofitInstance.api.getUsuarioLogado().enqueue(object : Callback<UsuarioResponse> {
            override fun onResponse(call: Call<UsuarioResponse>, response: Response<UsuarioResponse>) {
                if (response.isSuccessful) {
                    val usuario = response.body()
                    if (usuario != null) {
                        nomeTextView.text = usuario.nmUsuario
                        usuarioTextView.text = usuario.login
                        emailTextView.text = usuario.nmEmail
                        senhaTextView.text = "********"
                        Log.d(tag, "Dados do usuário carregados com sucesso.")
                    }
                } else {
                    Log.e(tag, "Erro ao carregar os dados: ${response.code()}")
                    Toast.makeText(context, "Erro ao carregar os dados do usuário.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UsuarioResponse>, t: Throwable) {
                Log.e(tag, "Erro na requisição: ${t.message}", t)
                Toast.makeText(context, "Erro na conexão. Verifique sua internet.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun mostrarPopupEdicao() {
        val alertDialog = AlertDialog.Builder(requireContext())
        val popupView = layoutInflater.inflate(R.layout.popup_editar_perfil, null)
        alertDialog.setView(popupView)

        val nomeEditText = popupView.findViewById<EditText>(R.id.inputNome)
        val usuarioEditText = popupView.findViewById<EditText>(R.id.inputUsuario)
        val emailEditText = popupView.findViewById<EditText>(R.id.inputEmail)
        val senhaEditText = popupView.findViewById<EditText>(R.id.inputSenha)

        nomeEditText.setText(nomeTextView.text)
        usuarioEditText.setText(usuarioTextView.text)
        emailEditText.setText(emailTextView.text)

        alertDialog.setPositiveButton("Confirmar") { dialog, _ ->
            val usuarioRequest = UsuarioRequest(
                nomeEditText.text.toString().trim(),
                usuarioEditText.text.toString().trim(),
                emailEditText.text.toString().trim(),
                senhaEditText.text.toString().trim()
            )
            atualizarDadosUsuario(usuarioRequest)
            dialog.dismiss()
        }

        alertDialog.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }

        alertDialog.show()
    }

    private fun atualizarDadosUsuario(usuarioRequest: UsuarioRequest) {
        RetrofitInstance.api.updateUsuario(usuarioRequest).enqueue(object : Callback<UsuarioResponse> {
            override fun onResponse(call: Call<UsuarioResponse>, response: Response<UsuarioResponse>) {
                if (response.isSuccessful) {
                    val usuarioAtualizado = response.body()
                    if (usuarioAtualizado != null) {
                        nomeTextView.text = usuarioAtualizado.nmUsuario
                        usuarioTextView.text = usuarioAtualizado.login
                        emailTextView.text = usuarioAtualizado.nmEmail
                        senhaTextView.text = "********"
                        Toast.makeText(context, "Dados atualizados com sucesso!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e(tag, "Erro ao atualizar os dados: ${response.code()}")
                    Toast.makeText(context, "Erro ao atualizar os dados.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UsuarioResponse>, t: Throwable) {
                Log.e(tag, "Erro na requisição: ${t.message}", t)
                Toast.makeText(context, "Erro na conexão. Verifique sua internet.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun confirmarDelecao() {
        AlertDialog.Builder(requireContext())
            .setTitle("Excluir conta")
            .setMessage("Tem certeza de que deseja excluir sua conta?")
            .setPositiveButton("Sim") { _, _ ->
                deletarConta()
            }
            .setNegativeButton("Não", null)
            .show()
    }

    private fun deletarConta() {
        RetrofitInstance.api.deleteUsuario().enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    sharedPreferences.edit().clear().apply()
                    startActivity(Intent(activity, LoginActivity::class.java))
                    activity?.finish()
                } else {
                    Log.e(tag, "Erro ao excluir conta: ${response.code()}")
                    Toast.makeText(context, "Erro ao excluir a conta.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e(tag, "Erro na requisição: ${t.message}", t)
                Toast.makeText(context, "Erro na conexão. Verifique sua internet.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
