package com.example.progetto_programmazionemobile.View

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import com.example.progetto_programmazionemobile.R
import com.example.progetto_programmazionemobile.ViewModel.Auth_Handler

class infoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_info, container, false)

        val welcomeText = v.findViewById<TextView>(R.id.welcome)
        welcomeText.setText("Benvenuto "+ (Auth_Handler.CURRENT_USER!!.nome!!.capitalize())+"!")

        val btnUnisciti : Button = v.findViewById(R.id.btnUniscitiPrenotazione)
        val btnPrenota : Button = v.findViewById(R.id.btnPrenotazione)


        //Nuova Prenotazione

        btnPrenota.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val goToSelection = Intent(v?.context,Selezione_1::class.java)
                startActivity(goToSelection)
            }
        })




        //Partecipa a prenotazione

        btnUnisciti.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                TODO("Not yet implemented")
            }
        })

        return v

    }

}