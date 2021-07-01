package com.example.progetto_programmazionemobile.Model

import java.io.Serializable
import java.util.*


class Utente(name: String, surname: String, nascita : Date?, user : String,email : String?,telefono : String?, password : String) : Serializable{

    var username : String = user
    var nome : String = name
    var cognome : String = surname
    var nascita : Date? = nascita
    var email : String? = email
    var telefono : String? = telefono
    var password : String = password




}