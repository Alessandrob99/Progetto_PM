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
import com.example.progetto_programmazionemobile.ViewModel.DB_Handler_Users

class RicercaGiocatori : Fragment() {

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_ricerca_giocatori, container, false)


        val db_conn = DB_Handler_Users()
        val ricercaBtn = v.findViewById<Button>(R.id.btnRicerca)
        var queryUserNome = v.findViewById<EditText>(R.id.RicercaNome)
        var queryUserCognome = v.findViewById<EditText>(R.id.RicercaCognome)


        ricercaBtn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if (v != null) {
                    if((TextUtils.isEmpty(queryUserNome.getText().toString()))&&(TextUtils.isEmpty(queryUserCognome.getText().toString()))){
                        Toast.makeText(this@RicercaGiocatori.context, "Inserire Qualcosa", Toast.LENGTH_SHORT).show()
                    }else{
                        val queryNome = queryUserNome.text.toString()
                        val queryCognome = queryUserCognome.text.toString()
                        //NEANCHE UN CAMPO MODIFICATO
                        if(((TextUtils.equals(queryNome,""))&&((TextUtils.equals(queryCognome,""))))){
                            Toast.makeText(this@RicercaGiocatori.context, "Inserire Qualcosa", Toast.LENGTH_SHORT).show()
                        }
                        //NOME INSERITO - COGNOME NO
                        if(((!TextUtils.equals(queryNome,""))&&((TextUtils.equals(queryCognome,""))))){
                            db_conn.SearchUsersByName(queryNome, object : DB_Handler_Users.MyCallbackFoundUsers {
                                override fun onCallback(returnUser: ArrayList<Utente>) {
                                    //INTENT TO ACTIVITY FOR RESULTS
                                    val intent = Intent(this@RicercaGiocatori.context, SearchResult::class.java)
                                    intent.putExtra("usersList", returnUser)
                                    startActivity(intent)
                                }
                            })
                        }
                        //NOME NO - COGNOME INSERITO
                        if(((TextUtils.equals(queryNome,""))&&((!TextUtils.equals(queryCognome,""))))){
                            db_conn.SearchUsersBySurname(queryCognome, object : DB_Handler_Users.MyCallbackFoundUsers {
                                override fun onCallback(returnUser: ArrayList<Utente>) {
                                    //INTENT TO ACTIVITY FOR RESULTS
                                    val intent = Intent(this@RicercaGiocatori.context, SearchResult::class.java)
                                    intent.putExtra("usersList", returnUser)
                                    startActivity(intent)
                                }
                            })
                        }
                        //NOME INSERITO - COGNOME INSERITO
                        if(((!TextUtils.equals(queryNome,""))&&((!TextUtils.equals(queryCognome,""))))){
                            db_conn.SearchUsersByNameANDSurname(queryNome,queryCognome, object : DB_Handler_Users.MyCallbackFoundUsers {
                                override fun onCallback(returnUser: ArrayList<Utente>) {
                                    //INTENT TO ACTIVITY FOR RESULTS
                                    val intent = Intent(this@RicercaGiocatori.context, SearchResult::class.java)
                                    intent.putExtra("usersList", returnUser)
                                    startActivity(intent)
                                }
                            })
                        }
                        /*
                        if (TextUtils.equals(query,"Ricerca")) {
                            Toast.makeText(this@RicercaGiocatori.context, "Inserire qualcosa", Toast.LENGTH_SHORT).show()

                        } else {
                            db_conn.SearchUsersByName(query, object : DB_Handler.MyCallbackFoundUsers {
                                override fun onCallback(returnUser: ArrayList<Utente>) {
                                    //INTENT TO ACTIVITY FOR RESULTS
                                    val intent = Intent(this@RicercaGiocatori.context, SearchResult::class.java)
                                    intent.putExtra("usersList", returnUser)
                                    startActivity(intent)
                                }
                            })

                        }

                         */
                    }

                }

            }
        })
        return v
    }
}

