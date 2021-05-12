package com.example.progetto_programmazionemobile.ViewModel

import com.example.progetto_programmazionemobile.Model.Utente
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DB_Handler {

    private var database = Firebase.database("https://prova-ee7d6-default-rtdb.europe-west1.firebasedatabase.app/")
    private var myRef = database.getReference()
    fun writeUser(name: String,surname: String, age: Int, id : String){
        val user = Utente(name,surname,age)
        myRef.child("users").child(id).setValue(user)
    }

}