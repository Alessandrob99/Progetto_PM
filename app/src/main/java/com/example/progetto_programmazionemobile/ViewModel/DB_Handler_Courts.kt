package com.example.progetto_programmazionemobile.ViewModel

import com.example.progetto_programmazionemobile.Model.Campo
import com.example.progetto_programmazionemobile.Model.Circolo
import com.google.firebase.firestore.FirebaseFirestore

class DB_Handler_Courts {

    //Callback function per i campi
    interface MyCallbackCourts{
        fun onCallback(returnedCourts: ArrayList<Campo>?)
    }
    companion object{
        val myRef = FirebaseFirestore.getInstance()

        //Funzione di filtraggio per ora giorno e sport
        fun filterCourtsBySelezione1(sport : String,oraInizio : String,oraFine : String,day : String){


             myRef.collection("campo").whereEqualTo("sport",sport).get().addOnSuccessListener{ document->
                 val data = document.documents  // Tutti i campi per quel dato SPORT
                 for(record in data){
                     var circolo : String = record.data?.get("id_circolo").toString() // ritorna questo->    projects/prova-ee7d6/databases/(default)/documents/clubs/1
                     var n_campo : Double = record.data?.get("n_campo") as Double

                 }
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
                            record.data?.get("n_campo") as Int,
                            club_id,
                            record.data?.get("superficie").toString(),
                            record.data?.get("sport") as ArrayList<String>,
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