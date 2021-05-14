package com.example.progetto_programmazionemobile.ViewModel

import android.content.ContentValues.TAG
import android.content.Context
import android.nfc.Tag
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast

import com.example.progetto_programmazionemobile.Model.Utente
import com.example.progetto_programmazionemobile.View.MainActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import java.util.concurrent.Flow
import javax.security.auth.callback.Callback
import kotlin.concurrent.thread

class DB_Handler {

    val myRef = FirebaseFirestore.getInstance()


    fun writeUser(name: String, surname: String, age: Long, id: String) {
        val user = Utente(name, surname, age, id)
    }



    fun readUser(id : String, myCallBack : MyCallback) {
           myRef.collection("users").document(id).get().addOnSuccessListener { document ->
               //Utente trovato
               val data = document.data
               val name = data?.get("nome").toString()
               val surname = data?.get("cognome").toString()
               val age = data?.get("eta") as Long
               val user = Utente(name,surname,age,id)
                myCallBack.onCallback(user)
           }
    }
    interface MyCallback {
        fun onCallback(returnValue: Utente)
    }








    /*val docRef : DocumentReference? = myRef.collection("users").document(id)
    if (docRef==null){
        return null
    }
    val docSnap = docRef.get()
    val docSnapRes = docSnap.result
    Thread.sleep(2000)

    val name = docSnapRes?.get("nome").toString()
    val surname = docSnapRes?.get("cognome").toString()
    val age = docSnapRes?.get("eta") as Long


    val returnedUser = Utente(name,surname,age,id)
    return returnedUser
}

     */
}


