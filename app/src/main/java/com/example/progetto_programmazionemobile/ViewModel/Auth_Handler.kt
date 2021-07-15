package com.example.progetto_programmazionemobile.ViewModel

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.example.progetto_programmazionemobile.Model.Utente
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.sql.Timestamp
import java.util.*
import kotlin.collections.ArrayList



class Auth_Handler  {


    companion object {

        val myRef = FirebaseFirestore.getInstance()


        var LOGGED_IN = false
        var CURRENT_USER : Utente? = null

        fun setLOGGED_IN() {
            LOGGED_IN = true
        }

        fun setLOGGED_IN(context: Context, ricordami: Boolean, email: String, password: String,myCallBack : MyCallBackResult) {
            LOGGED_IN = true
            if (ricordami) {
                var sharedPreferences: SharedPreferences? =
                    context?.getSharedPreferences("remember", Context.MODE_PRIVATE)
                var editor: SharedPreferences.Editor? = sharedPreferences?.edit()
                if (editor != null) {
                    editor.putString("email", email)
                    editor.putString("password", password)
                    editor.putBoolean("remember", true)
                    editor.apply()
                }
            }
            DB_Handler_Users.SearchUsersByEmail(email,object : DB_Handler_Users.MyCallbackFoundUser{
                override fun onCallback(returnUser: Utente) {
                    CURRENT_USER = Utente(
                        returnUser.nome,
                        returnUser.cognome,
                        returnUser.email,
                        returnUser.telefono,
                        returnUser.password
                    )
                    myCallBack.onCallBack(true,"")
                }
            })
        }

        fun setLOGGET_OUT(context: Context) {
            LOGGED_IN = false
            CURRENT_USER = null
            var sharedPreferences: SharedPreferences? =
                context?.getSharedPreferences("remember", Context.MODE_PRIVATE)
            var editor: SharedPreferences.Editor? = sharedPreferences?.edit()
            if (editor != null) {
                editor.putString("email", "")
                editor.putString("password", "")
                editor.putBoolean("remember", false)
                editor.apply()
            }
        }

        val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()


        fun FireBaseRegistration(
            email: String,
            password: String,
            nome : String,
            cognome : String,
            telefono : String,
            myCallBack: MyCallBackResult
        ) {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                firebaseAuth.currentUser!!.sendEmailVerification()
                    .addOnSuccessListener {                //  MAIL INVIATA CON SUCCESSO
                        DB_Handler_Users.newUser(password,nome,cognome,email,telefono)
                        myCallBack.onCallBack(true,"Registrazione avvenuta."+System.getProperty("line.separator")+"Validate il vostro account cliccando sul link contenuto nella mail che abbiamo inviato all'indirrizzo :"+System.getProperty("line.separator")+email)
                    }.addOnFailureListener {
                    myCallBack.onCallBack(false,it.message!!)
                }
            }.addOnFailureListener {
                if(it.message!! == "The email address is already in use by another account."){
                    myCallBack.onCallBack(false,"E-mail già in uso.")

                }else{
                    myCallBack.onCallBack(false,it.message!!)

                }


            }
        }

        fun FireBaseLogin(
            ricordami: Boolean,
            context: Context,
            email: String,
            password: String,
            myCallBack: MyCallBackResult
        ) {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                if(firebaseAuth.currentUser!!.isEmailVerified){
                    setLOGGED_IN(context,ricordami,email,password,object : MyCallBackResult{
                        override fun onCallBack(result: Boolean, message: String) {
                            myCallBack.onCallBack(true,"")
                        }
                    })
                }else{
                    myCallBack.onCallBack(false,"Verifica la tua email")
                }
            }.addOnFailureListener {
                when(it.message){
                    "The password is invalid or the user does not have a password."->
                        myCallBack.onCallBack(false,"Password errata.")
                    "There is no user record corresponding to this identifier. The user may have been deleted."->
                        myCallBack.onCallBack(false,"E-mail non valida.")
                    "The email address is badly formatted."->
                        myCallBack.onCallBack(false,"Ricontrollare l'indirizzo e-mail"+System.getProperty("line.separator")+"(Non devono essere presenti spazi alla fine)")
                    else-> myCallBack.onCallBack(false,it.message!!)
                }
            }

        }


        interface MyCallBackResult {
            fun onCallBack(result: Boolean,message:String)
        }


    }
}

