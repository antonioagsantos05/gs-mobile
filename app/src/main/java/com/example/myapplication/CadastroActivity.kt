package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.model.CadastroRequest
import com.example.myapplication.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CadastroActivity : AppCompatActivity() {

    private val TAG = "CadastroActivity"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        val loginLink: TextView = findViewById(R.id.tv_login)
        val cadastroButton: Button = findViewById(R.id.btn_cadastrar)
        val nomeEditText: EditText = findViewById(R.id.inputNome)
        val emailEditText: EditText = findViewById(R.id.inputEmail)
        val usuarioEditText: EditText = findViewById(R.id.inputUsuario)
        val senhaEditText: EditText = findViewById(R.id.inputSenha)

        loginLink.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        cadastroButton.setOnClickListener {
            val nome = nomeEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val usuario = usuarioEditText.text.toString().trim()
            val senha = senhaEditText.text.toString().trim()

            if (nome.isEmpty() || email.isEmpty() || usuario.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            fazerCadastro(nome, email, usuario, senha)
        }
    }

    private fun fazerCadastro(nome: String, email: String, usuario: String, senha: String) {

        val cadastroRequest = CadastroRequest(
            nmUsuario = nome,
            login = usuario,
            password = senha,
            nmEmail = email,
            role = "USER" // Define o papel do usuário como USER
        )

        Log.d(TAG, "Enviando JSON para cadastro: $cadastroRequest")


        RetrofitInstance.api.registrar(cadastroRequest).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d(TAG, "Requisição enviada - Código de resposta: ${response.code()}")

                if (response.isSuccessful) {
                    Log.d(TAG, "Cadastro realizado com sucesso")
                    Toast.makeText(this@CadastroActivity, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()

                    // Redireciona para a tela de login
                    val intent = Intent(this@CadastroActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Erro desconhecido"
                    Log.e(TAG, "Erro no cadastro - Código de resposta: ${response.code()}, Mensagem: $errorMessage")
                    Toast.makeText(this@CadastroActivity, "Erro no cadastro: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e(TAG, "Falha na requisição: ${t.message}", t)
                Toast.makeText(this@CadastroActivity, "Falha na requisição: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
