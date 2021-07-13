package com.example.progetto_programmazionemobile.ViewModel

import android.location.Location
import com.example.progetto_programmazionemobile.Model.Circolo
import com.google.firebase.firestore.FirebaseFirestore

class DB_Handler_Clubs {


    //Callback function per i circoli
    interface MyCallbackClubs{
        fun onCallback(returnedClubs: ArrayList<Circolo>?)
    }

    interface MyCallbackClub{
        fun onCallback(returnedClub : Circolo)
    }
    companion object{
        val myRef = FirebaseFirestore.getInstance()


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
                        record.data?.get("telefono").toString(),
                        record.data?.get("docce") as Boolean,
                        record.getGeoPoint("posizione")!!.latitude,
                        record.getGeoPoint("posizione")!!.longitude)

                    )
                }
                myCallBack.onCallback(clubs)
            }
        }

        //Query per tutti i campi in un dato range
        fun getAllClubsInRange(myCallBack : MyCallbackClubs,centre : Location,radius : Float){
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
                            record.data?.get("telefono").toString(),
                            record.data?.get("docce") as Boolean,
                            record.getGeoPoint("posizione")!!.latitude,
                            record.getGeoPoint("posizione")!!.longitude)
                        )
                    }
                }
                myCallBack.onCallback(clubs)
            }
        }


        fun getClubByID(id : String,myCallBack: MyCallbackClub){
            myRef.collection("clubs").document(id).get().addOnSuccessListener{
                if(it!=null){
                    myCallBack.onCallback(Circolo(
                        it.data?.get("id_circolo") as Long,
                        it.data?.get("nome").toString(),
                        it.data?.get("email").toString(),
                        it.data?.get("telefono").toString(),
                        it.data?.get("docce") as Boolean,
                        it.getGeoPoint("posizione")!!.latitude,
                        it.getGeoPoint("posizione")!!.longitude)

                    )
                }
            }
        }



        fun getClubByPosition(latitude : Double, longitude : Double, myCallBack: MyCallbackClub){

            myRef.collection("clubs").get().addOnSuccessListener{   document->
                val data = document.documents
                for(record in data){
                    if(record.getGeoPoint("posizione")!!.latitude == latitude && record.getGeoPoint("posizione")!!.longitude == longitude){
                        myCallBack.onCallback(Circolo(
                            record.data?.get("id_circolo") as Long,
                            record.data?.get("nome").toString(),
                            record.data?.get("email").toString(),
                            record.data?.get("telefono").toString(),
                            record.data?.get("docce") as Boolean,
                            record.getGeoPoint("posizione")!!.latitude,
                            record.getGeoPoint("posizione")!!.longitude)

                        )
                    }
                }
            }
        }


    }

}