package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.data.perguntas
import com.example.myapplication.model.PerguntaModel

class PerguntaActivity : AppCompatActivity() {

    private lateinit var perguntasLayout: LinearLayout
    private lateinit var btnEnviar: Button
    private val respostasSelecionadas = mutableMapOf<Int, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pergunta)

        perguntasLayout = findViewById(R.id.perguntasLayout)
        btnEnviar = findViewById(R.id.btnEnviarRespostas)

        val perguntasSorteadas = perguntas.shuffled().take(5)
        exibirPerguntas(perguntasSorteadas)

        btnEnviar.setOnClickListener {
            verificarRespostas(perguntasSorteadas)
        }
    }

    private fun exibirPerguntas(perguntasSorteadas: List<PerguntaModel>) {
        perguntasSorteadas.forEach { pergunta ->
            val perguntaView = LayoutInflater.from(this).inflate(R.layout.item_pergunta, perguntasLayout, false)

            val tvPergunta = perguntaView.findViewById<TextView>(R.id.tvPergunta)
            val rgAlternativas = perguntaView.findViewById<RadioGroup>(R.id.rgAlternativas)

            tvPergunta.text = pergunta.descricao

            pergunta.alternativas.shuffled().forEach { alternativa ->
                val radioButton = RadioButton(this).apply {
                    text = alternativa.descricao
                    tag = alternativa.descricao
                }
                rgAlternativas.addView(radioButton)
            }

            rgAlternativas.setOnCheckedChangeListener { _, checkedId ->
                val selectedRadioButton = perguntaView.findViewById<RadioButton>(checkedId)
                val respostaSelecionada = selectedRadioButton?.tag as? String
                if (respostaSelecionada != null) {
                    respostasSelecionadas[pergunta.id] = respostaSelecionada
                    Log.d("PerguntaActivity", "Resposta selecionada para pergunta ${pergunta.id}: $respostaSelecionada")
                }
            }

            perguntasLayout.addView(perguntaView)
        }
    }

    private fun verificarRespostas(perguntas: List<PerguntaModel>) {
        var acertos = 0

        perguntas.forEach { pergunta ->
            val respostaCorreta = pergunta.alternativas.find { it.correta }?.descricao
            val respostaSelecionada = respostasSelecionadas[pergunta.id]

            if (respostaCorreta == respostaSelecionada) {
                acertos++
            }
        }

        salvarNoHistorico(acertos)
        mostrarResultado(acertos)
    }

    private fun salvarNoHistorico(acertos: Int) {
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val historicoPreferences = getSharedPreferences("historico_tentativas", Context.MODE_PRIVATE)

        val editor = historicoPreferences.edit()
        val historico = historicoPreferences.getStringSet("tentativas", mutableSetOf()) ?: mutableSetOf()

        val nomeUsuario = sharedPreferences.getString("username", "Usuário Desconhecido")

        historico.add("$nomeUsuario - $acertos acertos")

        editor.putStringSet("tentativas", historico)
        editor.apply()
    }


    private fun mostrarResultado(acertos: Int) {
        AlertDialog.Builder(this)
            .setTitle("Resultado")
            .setMessage("Você acertou $acertos de 5 perguntas.")
            .setPositiveButton("OK") { _, _ ->
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("navigate_to", "historico") // Navega para o histórico
                startActivity(intent)
                finish()
            }
            .show()
    }
}
