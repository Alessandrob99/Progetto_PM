package com.example.progetto_programmazionemobile.ViewModel

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.progetto_programmazionemobile.Model.Utente
import com.example.progetto_programmazionemobile.ViewModel.Auth_Handler.Companion.myRef
import com.google.firebase.firestore.FirebaseFirestore
import java.sql.Timestamp
import java.util.*
import kotlin.collections.ArrayList


class DB_Handler {
    val myRef = FirebaseFirestore.getInstance()


    //CALLBACK FUNCTION PER IL RETURN DEGLI UTENTI RICERCATI

    interface MyCallbackFoundUsers{
        fun onCallback(returnUsers: ArrayList<Utente>)
    }


    fun writeUser(name: String, surname: String, nascita : Timestamp?, username : String,email : String?,telefono : String?, password : String) {
        val user = Utente(name, surname, nascita,username,email,telefono,password)
        myRef.collection("users").document(username).set(user)
    }


//---RICERCA UTENTE PER ID -----------------------------------FORSE NON FUNZIONANTE

    fun readUser(id : String, myCallBack : MyCallbackUser) {
        var user : Utente
        myRef.collection("users").document(id).get().addOnSuccessListener { document ->
            val data = document
            val timestamp = data?.get("data_nascita") as com.google.firebase.Timestamp
            val milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
            val data_nascita = Date(milliseconds)
            user = Utente(data?.get("nome").toString(), data?.get("cognome").toString()
                , data_nascita ,data?.get("user_name").toString()
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


    companion object{

//------AGGIORNAMENTO UTENTE DATO USERNAME ---------------//


        fun updateUserByUsername(username : String?,name : String, surname : String, email: String, number : String, password : String){
            var user = myRef.collection("users").document(username!!)
            user.update(
                "nome",name,
                "cognome",surname,
                "email" , email,
                "telefono",number,
                "password",password
            ).addOnSuccessListener {
                //Aggiorno i campi nell'Auth Handler
                Auth_Handler.CURRENT_USER =Utente(name, surname,
                    Auth_Handler.CURRENT_USER!!.nascita,Auth_Handler.CURRENT_USER!!.username
                    ,email ,number,password)

            }
                .addOnFailureListener {
                        e -> Log.w(TAG, "Error updating document", e)
                }

        }

        fun newUser(username: String,password: String,name: String,surname: String,email: String,telefono: String,dataNascita : Date){
            val docData = hashMapOf(
                "user_name" to username,
                "password" to password,
                "nome" to name,
                "cognome" to surname,
                "data_nascita" to dataNascita,
                "email" to email,
                "telefono" to telefono
            )

            myRef.collection("users").document(username)
                .set(docData)
                .addOnSuccessListener {


                }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        }






    }

}




