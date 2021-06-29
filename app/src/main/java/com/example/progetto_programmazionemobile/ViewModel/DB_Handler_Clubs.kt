package com.example.progetto_programmazionemobile.ViewModel

import android.location.Location
import com.example.progetto_programmazionemobile.Model.Circolo
import com.example.progetto_programmazionemobile.Model.Utente
import com.google.firebase.firestore.FirebaseFirestore

class DB_Handler_Clubs {

    val myRef = FirebaseFirestore.getInstance()

    //Callback function per i circoli
    interface MyCallbackClubs{
        fun onCallback(returnedClubs: ArrayList<Circolo>?)
    }

    //Query per ogni circolo
    fun getAllClubs(myCallBack : MyCallbackClubs){
        var clubs : ArrayList<Circolo>? = ArrayList<Circolo>()
        myRef.collection("clubs").get().addOnSuccessListener{ document->
            val data = document.documents
            for(record in data){
                clubs?.add(Circolo(
                    record.data?.get("id_circolo") as Long,
                    record.data?.get("nome").toString(),
                    record.data?.get("email").toString(),
                    record.data?.get("telefono") as Long,
                    record.data?.get("docce") as Boolean,
                    record.getGeoPoint("posizione")!!.latitude,
                    record.getGeoPoint("posizione")!!.longitude)

                )
            }
            myCallBack.onCallback(clubs)
        }
    }

    //Query per tutti i campi in un dato ranger
    fun getAllClubsInRange(myCallBack : MyCallbackClubs,centre : Location,radius : Int){
        var clubs : ArrayList<Circolo>? = ArrayList<Circolo>()
        var clubLocation = Location("")
        myRef.collection("clubs").get().addOnSuccessListener{ document->
            val data = document.documents
            for(record in data){
                clubLocation.latitude = record.getGeoPoint("posizione")!!.latitude
                clubLocation.longitude = record.getGeoPoint("posizione")!!.longitude
                if(clubLocation.distanceTo(centre)<(radius*1000)){
                    clubs?.add(Circolo(
                        record.data?.get("id_circolo") as Long,
                        record.data?.get("nome").toString(),
                        record.data?.get("email").toString(),
                        record.data?.get("telefono") as Long,
                        record.data?.get("docce") as Boolean,
                        record.getGeoPoint("posizione")!!.latitude,
                        record.getGeoPoint("posizione")!!.longitude)

                    )
                }
            }
            myCallBack.onCallback(clubs)
        }
    }





}