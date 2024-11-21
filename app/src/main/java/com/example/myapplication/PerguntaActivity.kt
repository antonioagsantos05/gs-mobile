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

        // Inicializa os elementos da interface
        perguntasLayout = findViewById(R.id.perguntasLayout)
        btnEnviar = findViewById(R.id.btnEnviarRespostas)

        // Carrega as perguntas sorteadas e as exibe
        val perguntasSorteadas = perguntas.shuffled().take(5)
        exibirPerguntas(perguntasSorteadas)

        // Configura o botão "Enviar Respostas"
        btnEnviar.setOnClickListener {
            verificarRespostas(perguntasSorteadas)
        }
    }

    private fun exibirPerguntas(perguntasSorteadas: List<PerguntaModel>) {
        perguntasSorteadas.forEach { pergunta ->
            val perguntaView = LayoutInflater.from(this).inflate(R.layout.item_pergunta, perguntasLayout, false)

            // Referências dos elementos da pergunta
            val tvPergunta = perguntaView.findViewById<TextView>(R.id.tvPergunta)
            val rgAlternativas = perguntaView.findViewById<RadioGroup>(R.id.rgAlternativas)

            // Configura os textos da pergunta
            tvPergunta.text = pergunta.descricao

            // Adiciona as alternativas ao RadioGroup
            pergunta.alternativas.shuffled().forEach { alternativa ->
                val radioButton = RadioButton(this).apply {
                    text = alternativa.descricao
                    tag = alternativa.descricao // Guarda o valor da alternativa como "tag"
                }
                rgAlternativas.addView(radioButton)
            }

            // Listener para salvar a resposta selecionada
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

        // Obtém o nome do usuário salvo em SharedPreferences
        val nomeUsuario = sharedPreferences.getString("username", "Usuário Desconhecido")

        // Adiciona o registro ao histórico
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
