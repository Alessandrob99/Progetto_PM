package com.example.progetto_programmazionemobile.ViewModel

import android.content.ContentValues
import android.os.Build
import android.util.Log
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
import kotlin.time.hours
import kotlin.time.minutes

class DB_Handler_Reservation {

    //Callback function per i campi
    interface MyCallbackAvailable {
        fun onCallback(result: Boolean)
    }

    //Lega ogni prenotazione alla sua ora di inizio e ora di fine
    interface MyCallbackReservations {
        fun onCallback(reservations: ArrayList<Prenotazione>?)
    }

    //Interfaccia per la Callback della nuova prenotazione
    interface MyCallBackNewRes {
        fun onCallback(result: Boolean)
    }

    companion object {

        val myRef = FirebaseFirestore.getInstance()


        //--------DA TESTARE---------//
        @RequiresApi(Build.VERSION_CODES.O)
        fun checkAvailability(
            giorno: String,
            oraInizio: String,
            oraFine: String,
            campo: Long,
            circolo: Long,
            myCallbackAvailable: MyCallbackAvailable
        ) {

            var returnValue: Boolean = false

            //Formatter diversi
            var split = oraInizio.split(":")
            var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm", Locale.ITALY)

            if (split[0].length == 1) {
                formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy H:mm", Locale.ITALY)
            } else {
                formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm", Locale.ITALY)
            }
            val dtInizio = LocalDateTime.parse(giorno + " " + oraInizio, formatter)
            split = oraFine.split(":")
            if (split[0].length == 1) {
                formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy H:mm", Locale.ITALY)
            } else {
                formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm", Locale.ITALY)
            }
            val dtFine = LocalDateTime.parse(giorno + " " + oraFine, formatter)

            //Impostiamo un offset per calcolare il timestamp di 2 ore rispetto Londra
            val timestampInizio =
                dtInizio.atOffset(ZoneOffset.ofHours(2)).toInstant().toEpochMilli()
            val timestampFine = dtFine.atOffset(ZoneOffset.ofHours(2)).toInstant().toEpochMilli()
            myRef.collection("prenotazione")
                .document(circolo.toString() + "-" + campo.toString() + "-" + giorno)
                .collection("prenotazioni").get().addOnSuccessListener { document ->
                    val data = document.documents
                    for (prenotazione in data) {
                        var timestamp =
                            prenotazione?.get("oraInizio") as com.google.firebase.Timestamp
                        val prenStart = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
                        timestamp = prenotazione?.get("oraFine") as com.google.firebase.Timestamp
                        val prenEnd = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
                        if ((prenStart < timestampFine) && (prenEnd > timestampInizio)) {
                            returnValue = true
                        }
                    }
                    myCallbackAvailable.onCallback(returnValue)
                }
        }


        fun getListOfReservations(
            giorno: String,
            campo: Long,
            circolo: Long,
            myCallbackReservations: MyCallbackReservations
        ) {

            myRef.collection("prenotazione")
                .document(circolo.toString() + "-" + campo.toString() + "-" + giorno).get()
                .addOnCompleteListener {
                    if (it.result.exists()) {
                        //Esistono prenotazioni per quella giornata
                        myRef.collection("prenotazione")
                            .document(circolo.toString() + "-" + campo.toString() + "-" + giorno)
                            .collection("prenotazioni").get().addOnSuccessListener { document ->
                                val data = document.documents
                                val reservations = ArrayList<Prenotazione>()
                                for (reservation in data) {
                                    var prenotatore: DocumentReference =
                                        reservation.data!!.get("prenotatore") as DocumentReference
                                    var timestamp =
                                        reservation?.get("oraInizio") as com.google.firebase.Timestamp
                                    var milliseconds =
                                        timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
                                    val dtInizio = Date(milliseconds)
                                    timestamp =
                                        reservation?.get("oraFine") as com.google.firebase.Timestamp
                                    milliseconds =
                                        timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
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
                    } else {
                        //Nessuna prenotazione ancora registrata per quel giorno
                        myCallbackReservations.onCallback(null)
                    }
                }
        }

        fun newReservation(
            user : String,
            circolo: String,
            campo: String,
            giorno: String,
            oraInizio: Long,
            oraFine: Long,
            codice_prenotazione: String,
            myCallBackNewRes: MyCallBackNewRes
        ) {
            //Controllo se oraFine == Mezzanotte
            val cal = Calendar.getInstance()
            cal.time = Date(oraFine)
            var oraFineChecked : Long
            if(cal.get(Calendar.HOUR_OF_DAY)==0){
                oraFineChecked = oraFine+86399000

            }else{
                oraFineChecked = oraFine
            }
            val docData = hashMapOf(
                "oraInizio" to Timestamp(oraInizio),
                "oraFine" to Timestamp(oraFineChecked),
                "prenotatore" to myRef.document("/users/ales")
            )


            myRef.collection("prenotazione")
                .document(circolo.toString() + "-" + campo.toString() + "-" + giorno).get()
                .addOnCompleteListener {
                    if (it.result.exists()) {
                        //esistono prenotazioni per quel giorno

                        val dummy : String = "dummyText"
                        val dummyData = hashMapOf(
                            "dummy" to dummy
                        )

                        myRef.collection("prenotazione").document(
                            circolo.toString() + "-" + campo.toString() + "-"+ giorno)
                            .collection("prenotazioni").document(codice_prenotazione)
                            .set(docData)
                            .addOnSuccessListener {

                                myRef.collection("users").document(user).collection("prenotazioni").document(codice_prenotazione).set(docData).addOnSuccessListener{
                                    myCallBackNewRes.onCallback(true)
                                }.addOnFailureListener{
                                    myCallBackNewRes.onCallback(false)
                                }

                            }
                            .addOnFailureListener { myCallBackNewRes.onCallback(false) }




                    } else {
                        //non esistono--> Creo la nuova raccolta e riempio la prenotazione

                        val dummy : String = "dummyText"
                        val dummyData = hashMapOf(
                            "dummy" to dummy
                        )

                        myRef.collection("prenotazione")
                            .document(circolo + "-" + campo + "-" + giorno).set(dummyData).addOnSuccessListener{
                                myRef.collection("prenotazione").document(circolo + "-" + campo + "-" + giorno)
                                    .collection("prenotazioni").document(codice_prenotazione)
                                    .set(docData)
                                    .addOnSuccessListener {
                                        myRef.collection("users").document(user).collection("prenotazioni").document(codice_prenotazione).set(docData).addOnSuccessListener{
                                            myCallBackNewRes.onCallback(true)
                                        }.addOnFailureListener{
                                            myCallBackNewRes.onCallback(false)
                                        }
                                    }
                                    .addOnFailureListener { myCallBackNewRes.onCallback(false) }
                            }

                    }
                }
        }


        fun cipher(text: String, shift: Int): String {
            var result = ""
            val firstCharCode: Int = 'A'.toInt()
            val offset: Int = ('z' - 'A') + 1
            for (i in text.indices) {
                val oldCharCode: Int = text[i].toInt()
                val oldIdxInAlphabet = oldCharCode - firstCharCode
                val newIdxInAlphababet = (oldIdxInAlphabet + shift) % offset
                val newChar: Char = (firstCharCode + newIdxInAlphababet).toChar()
                result += newChar
            }
            return result
        }

        fun decipher(text: String, shift: Int): String {
            return cipher(text, shift * (-1))
        }

    }

}
