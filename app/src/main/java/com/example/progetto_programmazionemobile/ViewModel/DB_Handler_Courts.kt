package com.example.progetto_programmazionemobile.ViewModel

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.progetto_programmazionemobile.Model.Campo
import com.example.progetto_programmazionemobile.Model.Circolo
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.sql.Timestamp
import java.text.DateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DB_Handler_Courts {

    //Callback function per i campi
    interface MyCallbackCourts{
        fun onCallback(returnedCourts: ArrayList<Campo>?)
    }
    companion object{
        val myRef = FirebaseFirestore.getInstance()

        //Funzione di filtraggio per ora giorno e sport
        @RequiresApi(Build.VERSION_CODES.O)
        fun filterCourtsBySelezione1(sport : String, oraInizio : String, oraFine : String, day : String, myCallBack : MyCallbackCourts){

            val campiFiltrati = ArrayList<Campo>()

            //SU FIREBASE LE GIORNATE SONO INDICIZZATE TRAMITE ID_CIRCOLO-CAMPO-GG-MM-AAAA

            //day deve essere formattato gg-mm-aaaa

            val formatter : DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
            val timestampOraInizio = Timestamp.valueOf(day+" "+oraInizio)
            val timestampOraFine = Timestamp.valueOf(day+" "+oraFine)

            myRef.collection("campo").whereEqualTo("sport",sport).get().addOnSuccessListener{ document->
                val data = document.documents  // Tutti i campi per quel dato SPORT
                for(record in data){
                    var circolo : DocumentReference = record.data?.get("id_circolo") as DocumentReference // ritorna questo->    com.google.firebase.firestore.DocumentReference@c44a6e47
                    var n_campo : Long = record.data?.get("n_campo") as Long
                    var id_circolo = circolo.id
                    var key = id_circolo+n_campo.toString()+day //      day = gg-mm-AAAA
                    myRef.collection("prenotazione").document(key).collection("prenotazioni").whereLessThan("oraINizio",timestampOraFine).whereGreaterThan("oraFine",timestampOraInizio).get().addOnSuccessListener{
                        if(it.isEmpty){ // SE NON CI SONO PRENOTAZIONI CHE SI SOVRAPPONGONO A QUELL'ORARIO PRENDO IL CAMPO
                            campiFiltrati.add(
                                Campo(
                                    record.data!!.get("n_campo") as Long,
                                    id_circolo as Long,
                                    record.data!!.get("superficie").toString(),
                                    record.data!!.get("sport").toString(),
                                    record.data!!.get("prezzo") as Float,
                                    record.data!!.get("riscaldamento") as Boolean,
                                    record.data!!.get("coperto") as Boolean
                                )
                            )
                        }
                    }.addOnFailureListener{

                        //Nessuna prenotazione ancora registrata per quella giornata
                        campiFiltrati.add(
                            Campo(
                                record.data!!.get("n_campo") as Long,
                                id_circolo.toLong(),
                                record.data!!.get("superficie").toString(),
                                record.data!!.get("sport").toString(),
                                record.data!!.get("prezzo") as Float,
                                record.data!!.get("riscaldamento") as Boolean,
                                record.data!!.get("coperto") as Boolean
                            )
                        )
                    }
                }
            }

        }



        fun getCourtsBySport(sport : String, myCallBack: MyCallbackCourts){
            var campi = ArrayList<Campo>()
            myRef.collection("campo").get().addOnSuccessListener{   document->
                val data = document.documents
                for(record in data){
                    var sportArrayString = record.data!!.get("sport").toString()
                    val sportArray = sportArrayString.split("-")
                    if(sportArray.contains(sport.toLowerCase())){
                        var circolo : DocumentReference = record.data?.get("id_circolo") as DocumentReference // ritorna questo->    com.google.firebase.firestore.DocumentReference@c44a6e47
                        var id_circolo = circolo.id.toLong()
                        campi.add(
                            Campo(
                                record.data!!.get("n_campo") as Long,
                                id_circolo,
                                record.data!!.get("superficie").toString(),
                                record.data!!.get("sport").toString(),
                                record.data!!.get("prezzo").toString().toFloat(),
                                record.data!!.get("riscaldamento") as Boolean,
                                record.data!!.get("coperto") as Boolean
                            )
                        )
                    }
                }
                myCallBack.onCallback(campi)
            }
        }



        //Prende campi di un circolo specificato da ID
        fun getCourtsOfSpecificClub(club_id: Long, myCallBack: DB_Handler_Courts.MyCallbackCourts){
            var courts : ArrayList<Campo> = ArrayList()
            DB_Handler_Clubs.myRef.collection("campo").whereEqualTo("id_circolo",club_id).get().addOnSuccessListener{ document->
                val data = document.documents
                for(record in data){
                    courts?.add(
                        Campo(
                            record.data?.get("n_campo") as Long,
                            club_id,
                            record.data?.get("superficie").toString(),
                            record.data?.get("sport") as String,
                            record.data?.get("prezzo") as Float,
                            record.data?.get("riscaldamento") as Boolean,
                            record.data?.get("coperto") as Boolean
                        )
                    )
                }
            }
            myCallBack.onCallback(courts)
        }


    }
}