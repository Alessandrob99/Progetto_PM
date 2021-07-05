package com.example.progetto_programmazionemobile.View

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.progetto_programmazionemobile.R
import org.w3c.dom.Text

class DetailsClubs:AppCompatActivity() {
    lateinit var autocompleteDurata: AutoCompleteTextView
    lateinit var autocompleteInizio: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_club)

        var nomeClub : TextView = findViewById(R.id.nomeClub)
        val prova:TextView = findViewById(R.id.provaaaa)
        var myLat: Double = intent.getDoubleExtra("latitudine", 0.0)
        prova.text = myLat.toString()

        nomeClub.text = intent.getStringExtra("titleClub")



        val arrayOrario = arrayOf(
            "",
            "0:30",
            "1:00",
            "1:30",
            "2:00",
            "2:30",
            "3:00",
            "3:30",
            "4:00"
        )

        autocompleteDurata = findViewById(R.id.durataOra) as AutoCompleteTextView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayOrario)
        autocompleteDurata.setText(adapter.getItem(0).toString(), false)
        autocompleteDurata.setAdapter(adapter)

        autocompleteDurata.onItemSelectedListener =
            object : AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
                override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    TODO("Not yet implemented")
                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    Toast.makeText(applicationContext, "Prova", Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }

    }
}