package com.example.progetto_programmazionemobile.Model

import android.location.Location
import com.example.progetto_programmazionemobile.ViewModel.DB_Handler_Clubs

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

        fun filterClubs(campiPerSportDaFiltrare : ArrayList<Campo>,myLoc : Location,range : Float?,riscaldamento : Boolean,docce : Boolean,coperto : Boolean,superficie : String,prezzoMax : Float?,myCallbackClubs1: MyCallbackClubs) {

            var clubs = ArrayList<Circolo>()   // Lo andiamo a riempire man mano che scorriamo i filtri

            var campiPerSportFiltrati = ArrayList<Campo>()    // Lista dei campi SOPRAVVISSUTI al filtraggio
            for(campo in campiPerSportDaFiltrare){ campiPerSportFiltrati.add(campo) }

            //Controllo sulla distanza massima (Per primo cosi evitiamo di fare controlli inutili)
            if (range != null) {
                DB_Handler_Clubs.getAllClubsInRange(object : DB_Handler_Clubs.MyCallbackClubs {
                    override fun onCallback(returnedClubs: ArrayList<Circolo>?) {
                        if(returnedClubs!=null){
                            //Per ogni campo vedo se si trova in uno dei circoli nel range
                            var clubsIDs = ArrayList<String>()
                            clubs = returnedClubs!!
                            //For di filtraggio per gli altri paramentri
                            for (campo in campiPerSportDaFiltrare) {
                                //Controllo sulla superficie
                                if (superficie != "Tutte") {
                                    if (!campo.superficie.equals(superficie)) {
                                        campiPerSportFiltrati.remove(campo)
                                    }
                                }
                                //Controllo sulla presenza di riscaldamento
                                if (riscaldamento) {
                                    if (!campo.riscaldamento) {
                                        campiPerSportDaFiltrare.remove(campo)
                                    }
                                }
                                //Controllo sul prezzo massimo
                                if (prezzoMax != null) {
                                    if (campo.prezzo_ora > prezzoMax) {
                                        campiPerSportFiltrati.remove(campo)
                                    }
                                }
                            }
                            //Controllo sulla presenza di docce/spogliatoi
                            if (docce) {
                                if (returnedClubs != null) {
                                    for (club in returnedClubs) {
                                        if (club.docce) {
                                            clubs.remove(club)
                                        }
                                    }
                                }
                            }
                            //Troviamo i circoli (in range) corrispondenti ai campi filtrati
                            for (court in campiPerSportFiltrati) {
                                clubsIDs.add(court.id_club.toString())
                            }
                            for (record in returnedClubs) {
                                if (clubsIDs.contains(record.id.toString())) {
                                    clubs.remove(record)
                                }
                            }

                            myCallbackClubs1.onCallback(clubs)
                        }
                    }
                }, myLoc, range)
            } else {
                DB_Handler_Clubs.getAllClubs(object : DB_Handler_Clubs.MyCallbackClubs {
                    override fun onCallback(returnedClubs: ArrayList<Circolo>?) {
                        var clubsIDs = ArrayList<String>()
                        //For di filtraggio per gli altri paramentri
                        for (i in campiPerSportDaFiltrare.indices) {
                            //Controllo sulla superficie
                            if(superficie!="Tutte"){
                                if(!campiPerSportDaFiltrare[i].superficie.equals(superficie)){
                                    campiPerSportDaFiltrare.removeAt(i)
                                }
                            }
                            //Controllo sulla presenza di riscaldamento
                            if(riscaldamento){
                                if(!campiPerSportDaFiltrare[i].riscaldamento){
                                    campiPerSportDaFiltrare.removeAt(i)
                                }
                            }
                            //Controllo sul prezzo massimo
                            if(prezzoMax!=null){
                                if(campiPerSportDaFiltrare[i].prezzo_ora>prezzoMax){
                                    campiPerSportDaFiltrare.removeAt(i)
                                }
                            }
                        }
                        //Controllo sulla presenza di docce/spogliatoi
                        if(docce){
                            for(i in clubs.indices){
                                if(!clubs[i].docce){
                                    clubs.removeAt(i)
                                }
                            }
                        }
                        //Troviamo i circoli corrispondenti ai campi filtrati
                        for (court in campiPerSportFiltrati) {
                            clubsIDs.add(court.id_club.toString())
                        }
                        if (returnedClubs != null) {
                            for (record in returnedClubs) {
                                if (clubsIDs.contains(record.id.toString())) {
                                    clubs.add(record)
                                }
                            }
                        }
                        myCallbackClubs1.onCallback(clubs)
                    }

                })
            }
        }
    }

}