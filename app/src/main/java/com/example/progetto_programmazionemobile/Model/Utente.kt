package com.example.progetto_programmazionemobile.Model

import java.io.Serializable


class Utente(name: String, surname: String, age: Long, id : String) : Serializable{

    var id_user : String? = id
    var nome : String? = name
    var cognome : String? = surname
    var eta :Long? = age



}