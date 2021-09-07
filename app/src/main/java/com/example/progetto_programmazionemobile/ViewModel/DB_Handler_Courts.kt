package com.example.progetto_programmazionemobile.ViewModel

import com.example.progetto_programmazionemobile.Model.Court
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class DB_Handler_Courts {

    //Callback function per i campi
    interface MyCallbackCourts{
        fun onCallback(returnedCourts: ArrayList<Court>?)
    }
    companion object{
        val myRef = FirebaseFirestore.getInstance()


        fun getCourtsBySport(sport : String, myCallBack: MyCallbackCourts){
            var campi = ArrayList<Court>()
            myRef.collection("campo").get().addOnSuccessListener{   document->
                val data = document.documents
                for(record in data){
                    var sportArrayString = record.data!!.get("sport").toString()
                    val sportArray = sportArrayString.split("-")
                    if(sportArray.contains(sport.toLowerCase())){
                        var circolo : DocumentReference = record.data?.get("id_circolo") as DocumentReference // ritorna questo->    com.google.firebase.firestore.DocumentReference@c44a6e47
                        var id_circolo = circolo.id.toLong()
                        campi.add(
                            Court(
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
            var courts : ArrayList<Court> = ArrayList()
            DB_Handler_Clubs.myRef.collection("campo").whereEqualTo("id_circolo",club_id).get().addOnSuccessListener{ document->
                val data = document.documents
                for(record in data){
                    courts?.add(
                        Court(
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