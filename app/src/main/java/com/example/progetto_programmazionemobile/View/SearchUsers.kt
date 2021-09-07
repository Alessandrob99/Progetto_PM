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
import com.example.progetto_programmazionemobile.Model.User
import com.example.progetto_programmazionemobile.R
import com.example.progetto_programmazionemobile.ViewModel.DB_Handler_Users

class SearchUsers : Fragment() {

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_ricerca_giocatori, container, false)


        val ricercaBtn = v.findViewById<Button>(R.id.btnRicerca)
        var queryUserNome = v.findViewById<EditText>(R.id.RicercaNome)
        var queryUserCognome = v.findViewById<EditText>(R.id.RicercaCognome)



        ricercaBtn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                if (v != null) {

                    val cognome = queryUserCognome.text.toString().replace(" ","")
                    val nome = queryUserNome.text.toString().replace(" ","")

                    if((TextUtils.isEmpty(queryUserNome.text))&&(TextUtils.isEmpty(queryUserCognome.text))){
                        Toast.makeText(this@SearchUsers.context, "Inserire Qualcosa", Toast.LENGTH_SHORT).show()
                    }else{

                        //NEANCHE UN CAMPO MODIFICATO
                        if(((nome=="")&&((cognome=="")))){
                            Toast.makeText(this@SearchUsers.context, "Inserire Qualcosa", Toast.LENGTH_SHORT).show()
                        }
                        //NOME INSERITO - COGNOME NO
                        if(((nome!="")&&((cognome=="")))){
                            DB_Handler_Users.SearchUsersByName(nome, object : DB_Handler_Users.MyCallbackFoundUsers {
                                override fun onCallback(returnUser: ArrayList<User>) {
                                    //INTENT TO ACTIVITY FOR RESULTS
                                    val intent = Intent(this@SearchUsers.context, SearchResult::class.java)
                                    intent.putExtra("usersList", returnUser)
                                    startActivity(intent)
                                }
                            })
                        }
                        //NOME NO - COGNOME INSERITO
                        if(((nome=="")&&((cognome!="")))){
                            DB_Handler_Users.SearchUsersBySurname(cognome, object : DB_Handler_Users.MyCallbackFoundUsers {
                                override fun onCallback(returnUser: ArrayList<User>) {
                                    //INTENT TO ACTIVITY FOR RESULTS
                                    val intent = Intent(this@SearchUsers.context, SearchResult::class.java)
                                    intent.putExtra("usersList", returnUser)
                                    startActivity(intent)
                                }
                            })
                        }
                        //NOME INSERITO - COGNOME INSERITO
                        if(((nome!="")&&((cognome!="")))){
                            DB_Handler_Users.SearchUsersByNameANDSurname(nome,cognome, object : DB_Handler_Users.MyCallbackFoundUsers {
                                override fun onCallback(returnUser: ArrayList<User>) {
                                    //INTENT TO ACTIVITY FOR RESULTS
                                    val intent = Intent(this@SearchUsers.context, SearchResult::class.java)
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

