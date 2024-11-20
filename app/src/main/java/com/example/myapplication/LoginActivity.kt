package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.model.LoginRequest
import com.example.myapplication.model.LoginResponse
import com.example.myapplication.network.RetrofitInstance
import com.example.myapplication.network.TokenUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicializa o RetrofitInstance
        RetrofitInstance.initialize(applicationContext)

        // Referências das Views
        val cadastroLink: TextView = findViewById(R.id.tv_cadastro)
        val loginButton: Button = findViewById(R.id.btn_login)
        val usuarioEditText: EditText = findViewById(R.id.inputUsuario)
        val senhaEditText: EditText = findViewById(R.id.inputSenha)

        // Redirecionar para a tela de cadastro
        cadastroLink.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }

        // Ação do botão de login
        loginButton.setOnClickListener {
            val usuario = usuarioEditText.text.toString().trim()
            val senha = senhaEditText.text.toString().trim()

            if (usuario.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            } else {
                fazerLogin(usuario, senha)
            }
        }
    }

    private fun fazerLogin(usuario: String, senha: String) {
        val loginRequest = LoginRequest(
            login = usuario,
            password = senha
        )

        RetrofitInstance.api.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val token = response.body()?.token
                    val userId = response.body()?.id
                    if (token != null && userId != null) {
                        salvarCredenciais(token, userId)
                        Toast.makeText(this@LoginActivity, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show()
                        redirecionarParaMainActivity()
                    } else {
                        Toast.makeText(this@LoginActivity, "Erro no login: Credenciais não encontradas", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Erro desconhecido"
                    Toast.makeText(this@LoginActivity, "Erro ao fazer login: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Erro na conexão. Verifique sua internet.", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "Falha na requisição: ${t.message}")
            }
        })
    }

    private fun salvarCredenciais(token: String, userId: Long) {
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putString("jwt_token", token)
            putLong("user_id", userId)
            apply()
        }
        Log.d(TAG, "Token e ID do usuário salvos no SharedPreferences.")
    }

    private fun redirecionarParaMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
