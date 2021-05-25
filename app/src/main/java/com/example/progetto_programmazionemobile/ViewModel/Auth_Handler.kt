package com.example.progetto_programmazionemobile.ViewModel

import com.example.progetto_programmazionemobile.Model.Utente
import com.google.firebase.firestore.FirebaseFirestore
import java.sql.Timestamp
import java.util.*
import kotlin.collections.ArrayList



class Auth_Handler  {


    companion object{

        val myRef = FirebaseFirestore.getInstance()


        var LOGGED_IN = false
        var CURRENT_USER : Utente? = null

        fun setLOGGED_IN (){
            LOGGED_IN=true
        }

        fun setLOGGET_OUT(){
            LOGGED_IN = false
            CURRENT_USER = null
        }

        fun isLOGGED_IN () : Boolean{return LOGGED_IN}


        fun checkCredentials( userName : String, password : String,myCallBack : MyCallback){
            var user : Utente? = null
            myRef.collection("users").whereEqualTo("user_name",userName).whereEqualTo("password",password).get().addOnSuccessListener { document->
                if(document.isEmpty){
                    CURRENT_USER = null
                    setLOGGET_OUT()
                    myCallBack.onCallback()
                }else{
                    val data = document.documents[0]
                    val timestamp = data?.get("data_nascita") as com.google.firebase.Timestamp
                    val milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
                    val data_nascita = Date(milliseconds)
                    CURRENT_USER =Utente(data?.get("nome").toString(), data?.get("cognome").toString()
                            , data_nascita ,data?.get("user_name").toString()
                            ,data?.get("email").toString() ,data?.get("telefono").toString(),data?.get("password").toString())
                    setLOGGED_IN()
                    myCallBack.onCallback()
                }
            }.addOnFailureListener{
                CURRENT_USER = null
                setLOGGET_OUT()
                myCallBack.onCallback()
            }
        }
        interface MyCallback{
            fun onCallback()
        }
    }




}

