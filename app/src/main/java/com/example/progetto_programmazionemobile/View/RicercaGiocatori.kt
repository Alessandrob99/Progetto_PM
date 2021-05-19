package com.example.progetto_programmazionemobile.View

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.Nullable
import com.example.progetto_programmazionemobile.Model.Utente
import com.example.progetto_programmazionemobile.R
import com.example.progetto_programmazionemobile.ViewModel.DB_Handler

class RicercaGiocatori : Fragment() {



    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_ricerca_giocatori, container, false)
        val db_conn = DB_Handler()
        val ricercaBtn = v.findViewById<Button>(R.id.btnRicercaUtente)
        var queryUser = v.findViewById<EditText>(R.id.Ricerca)
        ricercaBtn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if (v != null) {
                    if(TextUtils.isEmpty(queryUser.getText().toString())){
                        Toast.makeText(this@RicercaGiocatori.context, "Inserire qualcosa", Toast.LENGTH_SHORT).show()
                    }else{

                        val query = queryUser.text.toString()
                        if (TextUtils.equals(query,"Ricerca")) {
                            Toast.makeText(this@RicercaGiocatori.context, "Inserire qualcosa", Toast.LENGTH_SHORT).show()
                        } else {
                            db_conn.SearchUsers(query, object : DB_Handler.MyCallbackFoundUsers {
                                override fun onCallback(returnUser: ArrayList<Utente>) {
                                    //INTENT TO ACTIVITY FOR RESULTS
                                    val intent = Intent(this@RicercaGiocatori.context, SearchResult::class.java)
                                    intent.putExtra("usersList", returnUser)
                                    startActivity(intent)
                                }
                            })

                        }
                    }

                }

            }
        })
        return v
    }
}