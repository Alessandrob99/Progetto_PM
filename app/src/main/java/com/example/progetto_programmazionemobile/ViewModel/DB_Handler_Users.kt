package com.example.progetto_programmazionemobile.ViewModel

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.progetto_programmazionemobile.Model.Utente
import com.example.progetto_programmazionemobile.ViewModel.Auth_Handler.Companion.myRef
import com.google.firebase.firestore.FirebaseFirestore
import java.sql.Timestamp
import java.time.DateTimeException
import java.util.*
import kotlin.collections.ArrayList


class DB_Handler_Users {
    val myRef = FirebaseFirestore.getInstance()


    //CALLBACK FUNCTION PER IL RETURN DEGLI UTENTI RICERCATI

    interface MyCallbackFoundUsers{
        fun onCallback(returnUsers: ArrayList<Utente>)
    }

    interface MyCallbackMessage{
        fun onCallback(message : String)
    }

    fun writeUser(name: String, surname: String, username : String,email : String?,telefono : String?, password : String) {
        val user = Utente(name, surname,username,email,telefono,password)
        myRef.collection("users").document(username).set(user)
    }


//---RICERCA UTENTE PER ID -----------------------------------FORSE NON FUNZIONANTE

    fun readUser(id : String, myCallBack : MyCallbackUser) {
        var user : Utente
        myRef.collection("users").document(id).get().addOnSuccessListener { document ->
            val data = document
            user = Utente(data?.get("nome").toString(), data?.get("cognome").toString()
                ,data?.get("user_name").toString()
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
                user = Utente(d.data?.get("nome").toString(), d.data?.get("cognome").toString()
                    ,d.data?.get("user_name").toString()
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
                    ,d.data?.get("user_name").toString()
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

                user = Utente(d.data?.get("nome").toString(), d.data?.get("cognome").toString()
                    ,d.data?.get("user_name").toString()
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
                "nome",name.toLowerCase(),
                "cognome",surname.toLowerCase(),
                "email" , email,
                "telefono",number,
                "password",password
            ).addOnSuccessListener {
                //Aggiorno i campi nell'Auth Handler
                Auth_Handler.CURRENT_USER =Utente(name, surname,
                    Auth_Handler.CURRENT_USER!!.username
                    ,email ,number,password)

            }
                .addOnFailureListener {
                        e -> Log.w(TAG, "Error updating document", e)
                }

        }
        //FUNZIONE PER L'AGGIUNTA DI UN NUOVO UTENTE
        fun newUser(username: String,password: String,name: String,surname: String,email: String,telefono: String,dataNascita : Date){
            val docData = hashMapOf(
                "user_name" to username.toLowerCase(),
                "password" to password,
                "nome" to name.toLowerCase(),
                "cognome" to surname.toLowerCase(),
                "data_nascita" to dataNascita,
                "email" to email,
                "telefono" to telefono
            )

            myRef.collection("users").document(username)
                .set(docData)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        }

        //FUNZIONE PER IL CONTROLLO DELLE CREDENZIALI GIA ESISTENTI
        fun checkCreds(username: String,email: String,telefono: String,myCallBack: MyCallbackMessage){
            myRef.collection("users").whereEqualTo("user_name",username).get().addOnSuccessListener {
                if(it.isEmpty){
                    myRef.collection("users").whereEqualTo("email",email).get().addOnSuccessListener {
                        if(it.isEmpty){
                            myRef.collection("users").whereEqualTo("telefono",telefono).get().addOnSuccessListener {
                                if(it.isEmpty){
                                    myCallBack.onCallback("OK")
                                }else{
                                    myCallBack.onCallback("Telefono già registrato")
                                }
                            }
                        }else{
                            myCallBack.onCallback("Email già in uso")
                        }
                    }
                }else{
                    myCallBack.onCallback("Username già in uso")
                }

            }

        }

    }

}




