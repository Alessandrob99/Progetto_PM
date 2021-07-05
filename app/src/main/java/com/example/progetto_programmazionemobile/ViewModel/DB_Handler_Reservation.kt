package com.example.progetto_programmazionemobile.ViewModel

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.progetto_programmazionemobile.Model.Campo
import com.example.progetto_programmazionemobile.Model.Prenotazione
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.type.DateTime
import java.sql.Time
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class DB_Handler_Reservation {

    //Callback function per i campi
    interface MyCallbackAvailable{
        fun onCallback(result: Boolean)
    }

    //Lega ogni prenotazione alla sua ora di inizio e ora di fine
    interface MyCallbackReservations{
        fun onCallback(reservations : ArrayList<Prenotazione>)
    }


    companion object{

        val myRef = FirebaseFirestore.getInstance()


        //--------DA TESTARE---------//
        @RequiresApi(Build.VERSION_CODES.O)
        fun checkAvailability(giorno : String, oraInizio : String, oraFine : String, campo : Long, circolo : Long, myCallbackAvailable: MyCallbackAvailable ){
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm",Locale.ITALY)
            val dtInizio = LocalDateTime.parse(giorno+" "+oraInizio, formatter)
            val dtFine = LocalDateTime.parse(giorno+" "+oraFine, formatter)
            val timestampInizio = dtInizio.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli()
            val timestampFine = dtFine.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli()
            myRef.collection("prenotazione").document(circolo.toString()+"-"+campo.toString()+"-"+giorno).collection("prenotazioni").get().addOnSuccessListener{ document->
                val data = document.documents
                for(prenotazione in data){
                    val prenStart = prenotazione.data!!.get("oraInizio") as Long
                    val prenEnd = prenotazione.data!!.get("oraFine") as Long
                    if((prenStart < timestampFine) && (prenEnd>timestampInizio)){
                        myCallbackAvailable.onCallback(true)
                    }
                }
                myCallbackAvailable.onCallback(false)
            }
        }


        fun getListOfReservations(giorno: String,campo: Long,circolo: Long,myCallbackReservations: MyCallbackReservations){
            myRef.collection("prenotazione").document(circolo.toString()+"-"+campo.toString()+"-"+giorno)
                .collection("prenotazioni").get().addOnSuccessListener{ document->
                    val data = document.documents
                    val reservations = ArrayList<Prenotazione>()
                    for(reservation in data){
                        var prenotatore : DocumentReference = reservation.data!!.get("prenotatore") as DocumentReference
                        var timestamp = reservation?.get("oraInizio") as com.google.firebase.Timestamp
                        var milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
                        val dtInizio = Date(milliseconds)
                        timestamp = reservation?.get("oraFine") as com.google.firebase.Timestamp
                        milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
                        val dtFine = Date(milliseconds)
                        reservations.add(
                            Prenotazione(
                                reservation.id,
                                prenotatore.id,
                                dtInizio,
                                dtFine
                            )
                        )
                    }
                    myCallbackReservations.onCallback(reservations)
                }
        }

    }




}