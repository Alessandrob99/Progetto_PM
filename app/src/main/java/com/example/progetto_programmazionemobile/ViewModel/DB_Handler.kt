package com.example.progetto_programmazionemobile.ViewModel

import com.example.progetto_programmazionemobile.Model.Utente
import com.google.firebase.firestore.FirebaseFirestore
import java.sql.Timestamp
import java.util.*
import kotlin.collections.ArrayList


class DB_Handler {

    val myRef = FirebaseFirestore.getInstance()


    fun writeUser(name: String, surname: String, nascita : Timestamp?, username : String,email : String?,telefono : String?, password : String) {
        val user = Utente(name, surname, nascita,username,email,telefono,password)
        myRef.collection("users").document(username).set(user)
    }


//---RICERCA UTENTE PER ID -----------------------------------FORSE NON FUNZIONANTE

    fun readUser(id : String, myCallBack : MyCallbackUser) {
        var user : Utente
           myRef.collection("users").document(id).get().addOnSuccessListener { document ->
               val data = document
               user = Utente(data?.get("nome").toString(), data?.get("cognome").toString()
                       , data?.get("data_nascita") as Timestamp ,data?.get("user_name").toString()
                       ,data?.get("email").toString() ,data?.get("telefono").toString(),data?.get("password").toString())
               myCallBack.onCallback(user)
           }
    }
    interface MyCallbackUser {
        fun onCallback(returnValue: Utente)
    }




//---RICERCA UTENTI per nome -----------------------------------


    fun SearchUsersByName(query : String, myCallBack: MyCallbackFoundUsers) {
        var users : ArrayList<Utente> = ArrayList<Utente>()
        var user : Utente

        myRef.collection("users").whereEqualTo("nome",query.toLowerCase()).get().addOnSuccessListener { document->
            val data = document.documents
            for(d in data){

                val timestamp = d.data?.get("data_nascita") as com.google.firebase.Timestamp
                val milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
                val data_nascita = Date(milliseconds)

                user = Utente(d.data?.get("nome").toString(), d.data?.get("cognome").toString()
                        , data_nascita ,d.data?.get("user_name").toString()
                        ,d.data?.get("email").toString() ,d.data?.get("telefono").toString(),d.data?.get("password").toString())
                users.add(user)
            }
            myCallBack.onCallback(users)
        }

    }


//-----------------------------------------------------------------------------------------------------------------------


//---RICERCA UTENTI per cognome -----------------------------------


    fun SearchUsersBySurname(query : String, myCallBack: MyCallbackFoundUsers) {
        var users : ArrayList<Utente> = ArrayList<Utente>()
        var user : Utente

        myRef.collection("users").whereEqualTo("cognome",query.toLowerCase()).get().addOnSuccessListener { document->
            val data = document.documents
            for(d in data){
                val timestamp = d.data?.get("data_nascita") as com.google.firebase.Timestamp
                val milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
                val data_nascita = Date(milliseconds)

                user = Utente(d.data?.get("nome").toString(), d.data?.get("cognome").toString()
                        , data_nascita ,d.data?.get("user_name").toString()
                        ,d.data?.get("email").toString() ,d.data?.get("telefono").toString(),d.data?.get("password").toString())
                users.add(user)
            }
            myCallBack.onCallback(users)
        }

    }


//-----------------------------------------------------------------------
    //-----------Ricerca per nome e cognome---------------------------------------------------

    fun SearchUsersByNameANDSurname(queryName : String, querySurname : String, myCallBack: MyCallbackFoundUsers) {
        var users : ArrayList<Utente> = ArrayList<Utente>()
        var user : Utente



        myRef.collection("users").whereEqualTo("nome",queryName.toLowerCase()).whereEqualTo("cognome",querySurname.toLowerCase()).get().addOnSuccessListener { document->
            val data = document.documents
            for(d in data){
                val timestamp = d.data?.get("data_nascita") as com.google.firebase.Timestamp
                val milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
                val data_nascita = Date(milliseconds)

                user = Utente(d.data?.get("nome").toString(), d.data?.get("cognome").toString()
                        , data_nascita ,d.data?.get("user_name").toString()
                        ,d.data?.get("email").toString() ,d.data?.get("telefono").toString(),d.data?.get("password").toString())
                users.add(user)
            }
            myCallBack.onCallback(users)
        }

    }


//CALLBACK FUNCTION PER IL RETURN DEGLI UTENTI RICERCATI

    interface MyCallbackFoundUsers{
        fun onCallback(returnUsers: ArrayList<Utente>)
    }


}


