package com.example.progetto_programmazionemobile.Model

class Circolo(name: String, id_club : String, town: String, id_campi : ArrayList<String>,address : String, email : String,tel : String,region : String)
{
    val nome = name
    val id = id_club
    val regione = region
    val citta = town
    val campi = id_campi
    val indirizzo = address
    val email = email
    val telefono = tel

}