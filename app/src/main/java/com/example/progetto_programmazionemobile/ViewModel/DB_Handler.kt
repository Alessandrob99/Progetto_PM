package com.example.progetto_programmazionemobile.ViewModel

import android.content.ContentValues.TAG
import android.content.Context
import android.nfc.Tag
import android.util.Log
import android.widget.Toast

import com.example.progetto_programmazionemobile.Model.Utente
import com.example.progetto_programmazionemobile.View.MainActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore

import com.google.firebase.ktx.Firebase
import java.util.*
import javax.security.auth.callback.Callback

class DB_Handler {

    val myRef = FirebaseFirestore.getInstance()

    fun writeUser(name : String, surname : String, age : Long, id : String){
        val user = Utente(name,surname,age,id)


        myRef.collection("users").document(id)
                .set(user).addOnSuccessListener(OnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot successfully written!")
                })
                .addOnFailureListener(OnFailureListener {
                    e -> Log.w(TAG, "Error writing document", e)
                })

    }

}


