package com.example.myapplication.model

data class PerguntaModel(
    val id: Int,
    val tema: String,
    val descricao: String,
    val alternativas: List<RespostaModel>
)
