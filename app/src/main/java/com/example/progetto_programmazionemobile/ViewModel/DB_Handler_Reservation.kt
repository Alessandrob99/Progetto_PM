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

            var returnValue : Boolean = false

            //Formatter diversi
            var split = oraInizio.split(":")
            var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm",Locale.ITALY)

            if(split[0].length==1){
                formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy H:mm",Locale.ITALY)
            }else{
                formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm",Locale.ITALY)
            }
            val dtInizio = LocalDateTime.parse(giorno+" "+oraInizio, formatter)
            split = oraFine.split(":")
            if(split[0].length==1){
                formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy H:mm",Locale.ITALY)
            }else{
                formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm",Locale.ITALY)
            }
            val dtFine = LocalDateTime.parse(giorno+" "+oraFine, formatter)

            //Impostiamo un offset per calcolare il timestamp di 2 ore rispetto Londra
            val timestampInizio = dtInizio.atOffset(ZoneOffset.ofHours(2)).toInstant().toEpochMilli()
            val timestampFine = dtFine.atOffset(ZoneOffset.ofHours(2)).toInstant().toEpochMilli()
            myRef.collection("prenotazione").document(circolo.toString()+"-"+campo.toString()+"-"+giorno).collection("prenotazioni").get().addOnSuccessListener{ document->
                val data = document.documents
                for(prenotazione in data){
                    var timestamp = prenotazione?.get("oraInizio") as com.google.firebase.Timestamp
                    val prenStart = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
                    timestamp = prenotazione?.get("oraFine") as com.google.firebase.Timestamp
                    val prenEnd = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
                    if((prenStart < timestampFine) && (prenEnd>timestampInizio)){
                        returnValue = true
                    }
                }
                myCallbackAvailable.onCallback(returnValue)
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