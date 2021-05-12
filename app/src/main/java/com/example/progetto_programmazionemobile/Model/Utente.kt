package com.example.progetto_programmazionemobile.Model

import com.google.firebase.database.IgnoreExtraProperties

class Utente(name: String, surname: String, age: Int) {
    var nome : String = name
    var cognome : String = surname
    var eta = age


}