package com.example.progetto_programmazionemobile.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.progetto_programmazionemobile.R

class Selezione_2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selezione_2)

        val giornoText : TextView = findViewById(R.id.giorno)
        val orainizioText : TextView = findViewById(R.id.orainizio)
        val orafineText : TextView = findViewById(R.id.orafine)
        val sport : TextView = findViewById(R.id.sportText)

        //Arrivano bene
        val giornooo = intent.getStringExtra("giorno")
        val sportttt = intent.getStringExtra("sport")



        val intent = intent
        sport.setText(intent.getStringExtra("sport"))
        orafineText.setText(intent.getStringExtra("orafine"))
        orainizioText.setText(intent.getStringExtra("orainizio"))
        giornoText.setText(intent.getStringExtra("giorno"))

    }
}