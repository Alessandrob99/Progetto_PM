package com.example.progetto_programmazionemobile.ViewModel

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DB_Handler {

    val db = Firebase.database
    val myRef = db.getReference("https://prova-ee7d6-default-rtdb.europe-west1.firebasedatabase.app/message")

    fun write(){
        myRef.setValue("Hello this ia a message from the app")
    }
}