package com.example.progetto_programmazionemobile.View

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.progetto_programmazionemobile.R
import org.w3c.dom.Text

class DetailsClubs:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_club)

        var nomeClub : TextView = findViewById(R.id.nomeClub)

        nomeClub.text = intent.getStringExtra("titleClub")
    }
}