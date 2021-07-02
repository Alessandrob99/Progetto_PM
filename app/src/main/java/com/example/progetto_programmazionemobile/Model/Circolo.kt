package com.example.progetto_programmazionemobile.Model

import android.location.Location
import com.example.progetto_programmazionemobile.ViewModel.DB_Handler_Clubs
import com.example.progetto_programmazionemobile.ViewModel.DB_Handler_Courts

class Circolo(id : Long,name: String, email : String,tel : String, docce : Boolean,lat : Double,lng:Double)
{
    //Parametri circolo
    val nome = name
    val id = id
    val email = email
    val telefono = tel
    val docce = docce
    val posizione = arrayListOf<Double>(lat,lng)



    //CallBack che rimanda i risultati filtrati all'activity Selezione_2
    interface MyCallbackClubs{
        fun onCallback(returnedCourts: ArrayList<Circolo>?)
    }
    companion object{

        fun filterClubs(campiPerSport : ArrayList<Campo>,myLoc : Location,range : Float?,riscaldamento : Boolean,docce : Boolean,coperto : Boolean,superficie : String?,prezzoMax : Float?,myCallbackClubs: MyCallbackClubs) {

            var clubs = ArrayList<Circolo>()   // Lo andiamo a riempire man mano che scorriamo i filtri

            //Controllo sulla distanza massima (Per primo cosi evitiamo di fare controlli inutili)
            if (range != null) {
                DB_Handler_Clubs.getAllClubsInRange(object : DB_Handler_Clubs.MyCallbackClubs {
                    override fun onCallback(returnedClubs: ArrayList<Circolo>?) {
                        //Per ogni campo vedo se si trova in uno dei circoli nel range
                        var clubsIDs = ArrayList<String>()
                        for (court in campiPerSport) {
                            clubsIDs.add(court.id_club.toString())
                        }
                        if (returnedClubs != null) {
                            for (record in returnedClubs) {
                                if (clubsIDs.contains(record.id.toString())) {
                                    clubs.add(record)
                                }
                            }
                        }


                        //Controllo sulla presenza di docce/spogliatoi

                        //Controllo sulla presenza di campi con la superficie specificata

                        //Controllo sulla presenza di campi con riscaldamento

                        //Controllo sul prezzo massimo
                        myCallbackClubs.onCallback(clubs)
                    }
                }, myLoc, range)
            } else {
                DB_Handler_Clubs.getAllClubs(object : DB_Handler_Clubs.MyCallbackClubs {
                    override fun onCallback(returnedClubs: ArrayList<Circolo>?) {
                        //Per ogni campo vedo se si trova in uno dei circoli nel range
                        var clubsIDs = ArrayList<String>()
                        for (court in campiPerSport) {
                            clubsIDs.add(court.id_club.toString())
                        }
                        if (returnedClubs != null) {
                            for (record in returnedClubs) {
                                if (clubsIDs.contains(record.id.toString())) {
                                    clubs.add(record)
                                }
                            }
                        }
                        //Controllo sulla presenza di docce/spogliatoi

                        //Controllo sulla presenza di campi con la superficie specificata

                        //Controllo sulla presenza di campi con riscaldamento

                        //Controllo sul prezzo massimo
                        myCallbackClubs.onCallback(clubs)
                    }

                })
            }
        }
    }

}