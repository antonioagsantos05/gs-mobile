package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

class Historico : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_historico, container, false)
        carregarHistorico(view)
        return view
    }

    private fun carregarHistorico(view: View) {
        val sharedPreferences = requireContext().getSharedPreferences("historico_tentativas", Context.MODE_PRIVATE)
        val historico = sharedPreferences.getStringSet("tentativas", mutableSetOf()) ?: mutableSetOf()

        val historicoLayout = view.findViewById<LinearLayout>(R.id.historicoLayout)
        historicoLayout.removeAllViews() // Limpa os itens existentes

        for (item in historico) {
            val itemView = LayoutInflater.from(context).inflate(R.layout.item_historico, historicoLayout, false)
            val tvHistorico = itemView.findViewById<TextView>(R.id.tvHistorico)
            tvHistorico.text = item
            historicoLayout.addView(itemView) // Adiciona o item no layout pai
        }
    }
}
