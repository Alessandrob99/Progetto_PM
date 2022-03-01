    package com.example.progetto_programmazionemobile.ViewModel

import android.content.Context
import android.content.SharedPreferences
import com.example.progetto_programmazionemobile.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


    class Auth_Handler  {


    companion object {

        val myRef = FirebaseFirestore.getInstance()


        var LOGGED_IN = false
        var CURRENT_USER : User? = null


        /**
         * Salva le credenziali dell'utente e anche il checkbox
         */
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
                override fun onCallback(returnUser: User) {
                    CURRENT_USER = User(
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

        /**
         * Rimuove le credenziali dell'utente e anche il checkbox
         */
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


        /**
         * Registrazione con invio conferma da parte di Firebase
         */
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

        /**
         * Login con controlli dell'email e password
         */
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
                        myCallBack.onCallBack(false,"E-mail mal formattata")
                    "A network error (such as timeout, interrupted connection or unreachable host) has occurred."->
                        myCallBack.onCallBack(false,"Errore di connessione."+System.getProperty("line.separator")+"Controllare che la connessione a internet si abilitata.")
                    else-> myCallBack.onCallBack(false,it.message!!)
                }
            }

        }


        fun updatePassword(newPassword : String){
            var firebaseuser = Firebase.auth.currentUser
            if (firebaseuser != null) {
                firebaseuser.updatePassword(newPassword)
            }
        }

        interface MyCallBackResult {
            fun onCallBack(result: Boolean,message:String)
        }


    }
}

