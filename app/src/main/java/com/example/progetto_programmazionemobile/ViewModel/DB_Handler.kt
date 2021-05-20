package com.example.progetto_programmazionemobile.ViewModel

import com.example.progetto_programmazionemobile.Model.Utente
import com.google.firebase.firestore.FirebaseFirestore


class DB_Handler {

    val myRef = FirebaseFirestore.getInstance()


    fun writeUser(name: String, surname: String, age: Long, id: String) {
        val user = Utente(name, surname, age, id)
        myRef.collection("users").document(id).set(user)
    }


//---RICERCA UTENTE PER ID -----------------------------------

    fun readUser(id : String, myCallBack : MyCallbackUser) {
           myRef.collection("users").document(id).get().addOnSuccessListener { document ->
               //Utente trovato
               val data = document.data
               val name = data?.get("nome").toString()
               val surname = data?.get("cognome").toString()
               val age = data?.get("eta") as Long
               val user = Utente(name,surname,age,id)
               myCallBack.onCallback(user)
           }
    }
    interface MyCallbackUser {
        fun onCallback(returnValue: Utente)
    }




//---RICERCA UTENTI per nome -----------------------------------


    fun SearchUsersByName(query : String, myCallBack: MyCallbackFoundUsers) {
        var users : ArrayList<Utente> = ArrayList<Utente>()
        var user : Utente

        myRef.collection("users").whereEqualTo("nome",query.toLowerCase()).get().addOnSuccessListener { document->
            val data = document.documents
            for(d in data){
                user = Utente(d.data?.get("nome").toString(), d.data?.get("cognome").toString(), d.data?.get("eta") as Long ,d.data?.get("id_user").toString())
                users.add(user)
            }
            myCallBack.onCallback(users)
        }

    }


//-----------------------------------------------------------------------------------------------------------------------


//---RICERCA UTENTI per cognome -----------------------------------


    fun SearchUsersBySurname(query : String, myCallBack: MyCallbackFoundUsers) {
        var users : ArrayList<Utente> = ArrayList<Utente>()
        var user : Utente

        myRef.collection("users").whereEqualTo("cognome",query.toLowerCase()).get().addOnSuccessListener { document->
            val data = document.documents
            for(d in data){
                user = Utente(d.data?.get("nome").toString(), d.data?.get("cognome").toString(), d.data?.get("eta") as Long ,d.data?.get("id_user").toString())
                users.add(user)
            }
            myCallBack.onCallback(users)
        }

    }


//-----------------------------------------------------------------------
    //-----------Ricerca per nome e cognome---------------------------------------------------

    fun SearchUsersByNameANDSurname(queryName : String, querySurname : String, myCallBack: MyCallbackFoundUsers) {
        var users : ArrayList<Utente> = ArrayList<Utente>()
        var user : Utente


        myRef.collection("users").whereEqualTo("nome",queryName.toLowerCase()).whereEqualTo("cognome",querySurname.toLowerCase()).get().addOnSuccessListener { document->
            val data = document.documents
            for(d in data){
                user = Utente(d.data?.get("nome").toString(), d.data?.get("cognome").toString(), d.data?.get("eta") as Long ,d.data?.get("id_user").toString())
                users.add(user)
            }
            myCallBack.onCallback(users)
        }

    }


//CALLBACK FUNCTION PER IL RETURN DEGLI UTENTI RICERCATI

    interface MyCallbackFoundUsers{
        fun onCallback(returnUsers: ArrayList<Utente>)
    }


}


