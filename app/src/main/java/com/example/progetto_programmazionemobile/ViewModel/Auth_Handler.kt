package com.example.progetto_programmazionemobile.ViewModel

import android.content.Context
import android.content.SharedPreferences
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
        fun setLOGGED_IN (context: Context,ricordami : Boolean,userName: String,password: String){
            LOGGED_IN=true
            if(ricordami) {
                var sharedPreferences : SharedPreferences? = context?.getSharedPreferences("remember", Context.MODE_PRIVATE)
                var editor : SharedPreferences.Editor? = sharedPreferences?.edit()
                if (editor != null) {
                    editor.putString("username",userName)
                    editor.putString("password",password)
                    editor.putBoolean("remember",true)
                    editor.apply()
                }
            }
        }

        fun setLOGGET_OUT(){
            LOGGED_IN = false
            CURRENT_USER = null
        }
        fun setLOGGET_OUT(context : Context){
            LOGGED_IN = false
            CURRENT_USER = null
            var sharedPreferences : SharedPreferences? = context?.getSharedPreferences("remember", Context.MODE_PRIVATE)
            var editor : SharedPreferences.Editor? = sharedPreferences?.edit()
            if (editor != null) {
                editor.putString("username","")
                editor.putString("password","")
                editor.putBoolean("remember",false)
                editor.apply()
            }
        }

        fun isLOGGED_IN () : Boolean{return LOGGED_IN}


        fun checkCredentials( userName : String, password : String,myCallBack : MyCallback){
            myRef.collection("users").whereEqualTo("user_name",userName).whereEqualTo("password",password).get().addOnSuccessListener { document->
                if(document.isEmpty){
                    CURRENT_USER = null
                    setLOGGET_OUT()
                    myCallBack.onCallback()
                }else{
                    val data = document.documents[0]

                    CURRENT_USER =Utente(data?.get("nome").toString(), data?.get("cognome").toString()
                            ,data?.get("user_name").toString()
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

