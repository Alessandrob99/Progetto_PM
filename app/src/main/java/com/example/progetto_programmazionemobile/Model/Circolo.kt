package com.example.progetto_programmazionemobile.Model

class Circolo(id : Long,name: String, email : String,tel : Long, docce : Boolean,lat : Double,lng:Double)
{
    val nome = name
    val id = id
    val email = email
    val telefono = tel
    val docce = docce
    val posizione = arrayListOf<Double>(lat,lng)
}