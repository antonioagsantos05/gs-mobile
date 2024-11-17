package com.example.myapplication

import android.annotation.SuppressLint
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Log.d(TAG, "onCreate: Iniciando LoginActivity")

        // Inicializar RetrofitInstance
        RetrofitInstance.initialize(applicationContext)

        // Referências das views
        val cadastroLink: TextView = findViewById(R.id.tv_cadastro)
        val loginButton: Button = findViewById(R.id.btn_login)
        val usuarioEditText: EditText = findViewById(R.id.inputUsuario)
        val senhaEditText: EditText = findViewById(R.id.inputSenha)

        cadastroLink.setOnClickListener {
            Log.d(TAG, "onCreate: Clicou em 'Cadastrar' - indo para CadastroActivity")
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            val usuario = usuarioEditText.text.toString().trim()
            val senha = senhaEditText.text.toString().trim()

            if (usuario.isNotEmpty() && senha.isNotEmpty()) {
                fazerLogin(usuario, senha)
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
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

                    if (token != null) {
                        // Salva o token no SharedPreferences
                        val sharedPreferences =
                            getSharedPreferences("user_data", Context.MODE_PRIVATE)
                        sharedPreferences.edit().apply {
                            putString("jwt_token", token)
                            apply()
                        }
                        Log.d(TAG, "Token JWT salvo no SharedPreferences: $token")

                        // Redireciona para a MainActivity
                        Toast.makeText(
                            this@LoginActivity,
                            "Login realizado com sucesso!",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    } else {
                        Log.w(TAG, "Token JWT não retornado pela API")
                        Toast.makeText(
                            this@LoginActivity,
                            "Erro no login: Token não encontrado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Erro desconhecido"
                    Log.e(TAG, "Erro ao fazer login - Código: ${response.code()}, Mensagem: $errorMessage")
                    Toast.makeText(
                        this@LoginActivity,
                        "Erro ao fazer login: $errorMessage",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e(TAG, "Falha na requisição: ${t.message}")
                Toast.makeText(
                    this@LoginActivity,
                    "Erro na conexão. Verifique sua internet.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
