package com.example.progetto_programmazionemobile.ViewModel

import com.google.firebase.firestore.FirebaseFirestore
import java.sql.Time
import java.sql.Timestamp

class DB_Handler_Reservation {

    val myRef = FirebaseFirestore.getInstance()


    companion object{

        fun checkAvailability(giorno : String, oraInizio : String, oraFine : String, campo : Long,circolo : Long){

        }

    }

}