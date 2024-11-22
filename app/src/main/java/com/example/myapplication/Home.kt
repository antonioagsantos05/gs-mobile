package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class Home : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val btnIniciarQuiz: Button = view.findViewById(R.id.btn_tirar_foto)

        btnIniciarQuiz.setOnClickListener {
            redirecionarParaPerguntaActivity()
        }

        return view
    }

    private fun redirecionarParaPerguntaActivity() {
        val intent = Intent(requireContext(), PerguntaActivity::class.java)
        startActivity(intent)
    }
}
