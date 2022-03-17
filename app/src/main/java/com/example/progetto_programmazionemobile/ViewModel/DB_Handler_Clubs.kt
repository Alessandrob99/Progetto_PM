package com.example.progetto_programmazionemobile.ViewModel

import android.content.ContentValues
import android.location.Location
import android.util.Log
import com.example.progetto_programmazionemobile.Model.Club
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint

class DB_Handler_Clubs {


    //Callback function per i circoli
    interface MyCallbackClubs{
        fun onCallback(returnedClubs: ArrayList<Club>?)
    }

    interface MyCallbackRequest{
        fun onCallback(esito : Boolean)
    }
    interface MyCallbackClub{
        fun onCallback(returnedClub : Club)
    }
    companion object{
        val myRef = FirebaseFirestore.getInstance()


        //Query per ogni circolo
        fun getAllClubs(myCallBack : MyCallbackClubs){
            var clubs : ArrayList<Club>? = ArrayList<Club>()
            myRef.collection("clubs").get().addOnSuccessListener{ document->
                val data = document.documents
                for(record in data){
                    clubs?.add(Club(
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
            var clubs : ArrayList<Club>? = ArrayList<Club>()
            var clubLocation = Location("")
            myRef.collection("clubs").get().addOnSuccessListener{ document->
                val data = document.documents
                for(record in data){
                    clubLocation.latitude = record.getGeoPoint("posizione")!!.latitude
                    clubLocation.longitude = record.getGeoPoint("posizione")!!.longitude
                    if(clubLocation.distanceTo(centre)<(radius*1000)){
                        clubs?.add(Club(
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
                    myCallBack.onCallback(Club(
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
                        myCallBack.onCallback(Club(
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

        fun newRequest(nome : String,email : String,telefono : String,lat : Double,lng : Double,docce: Boolean,callback : MyCallbackRequest){

            var location = GeoPoint(lat,lng)
            val docData = hashMapOf(
                "nome" to nome.toLowerCase(),
                "email" to email,
                "telefono" to telefono,
                "posizione" to location,
                "docce" to docce
            )

            Auth_Handler.myRef.collection("richieste_circoli").document(email)
                .set(docData)
                .addOnSuccessListener { callback.onCallback(true)}
                .addOnFailureListener { callback.onCallback(false) }
        }


    }

}