package com.example.progetto_programmazionemobile.Model

import android.location.Location
import com.example.progetto_programmazionemobile.ViewModel.DB_Handler_Clubs

class Circolo(id : Long,name: String, email : String,tel : Long, docce : Boolean,lat : Double,lng:Double)
{
    //CallBack che rimanda i risultati filtrati all'activity Selezione_2
    interface MyCallbackClubs{
        fun onCallback(returnedCourts: ArrayList<Circolo>?)
    }
    companion object{

        fun filterClubs(myLoc : Location,range : Float?,riscaldamento : Boolean,docce : Boolean,coperto : Boolean,superficie : String?,prezzoMax : Float?,myCallbackClubs: MyCallbackClubs) {



            var filteredClubs : ArrayList<Circolo>? = ArrayList<Circolo>()


            //Controllo sulla distanza massima (Per primo cosi evitiamo di fare controlli inutili)
            if(range != null){
                if((range!!)!=0.toFloat())
                    DB_Handler_Clubs.getAllClubsInRange(object : DB_Handler_Clubs.MyCallbackClubs{
                        override fun onCallback(returnedClubs: ArrayList<Circolo>?) {
                            filteredClubs = returnedClubs

                            myCallbackClubs.onCallback(filteredClubs)
                        }
                    },myLoc,range)
            }else{
                    DB_Handler_Clubs.getAllClubs(object : DB_Handler_Clubs.MyCallbackClubs{
                        override fun onCallback(returnedClubs: ArrayList<Circolo>?) {
                            filteredClubs = returnedClubs

                            myCallbackClubs.onCallback(filteredClubs)

                        }
                    })
            }
            /*inizio commento
            if(filteredClubs != null){
                //Controllo sulla presenza di docce/spogliatoi
                for(i in filteredClubs!!.indices){
                    if(filteredClubs!![i].docce == false){
                        filteredClubs!!.removeAt(i)
                    }
                }
                for(i in filteredClubs!!.indices){
                    DB_Handler_Clubs.getCourtsOfSpecificClub(filteredClubs!![i].id,object : DB_Handler_Clubs.MyCallbackCourts{
                        override fun onCallback(returnedCourts: ArrayList<Campo>?) {
                            if(returnedCourts!=null){
                                for(j in returnedCourts.indices){

                                }
                            }else{
                                filteredClubs!!.removeAt(i)
                            }

                        }
                    })
                }


                //Controllo sulla presenza di campi con la superficie specificata

                //Controllo sulla presenza di campi con riscaldamento

                //Controllo sul prezzo massimo

            }



            fine commento*/

        }
    }
    val nome = name
    val id = id
    val email = email
    val telefono = tel
    val docce = docce
    val posizione = arrayListOf<Double>(lat,lng)
}