package com.example.progetto_programmazionemobile.ViewModel

import android.content.ContentValues.TAG
import android.util.Log
import com.example.progetto_programmazionemobile.Model.Prenotazione
import com.example.progetto_programmazionemobile.Model.Utente
import com.example.progetto_programmazionemobile.ViewModel.Auth_Handler.Companion.myRef
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList


class DB_Handler_Users {
    val myRef = FirebaseFirestore.getInstance()
    //CALLBACK FUNCTION PER IL RETURN DEGLI UTENTI RICERCATI
    interface MyCallbackFoundUser {
        fun onCallback(returnUser: Utente)
    }
    interface MyCallbackFoundUsers {
        fun onCallback(returnUsers: ArrayList<Utente>)
    }

    interface MyCallbackReservations {
        fun onCallback(reservations: ArrayList<Prenotazione>?)
    }
    companion object {

        //---RICERCA UTENTI per nome -----------------------------------

        fun SearchUsersByName(query: String, myCallBack: MyCallbackFoundUsers) {
            var users: ArrayList<Utente> = ArrayList<Utente>()
            var user: Utente

            myRef.collection("users").whereEqualTo("nome", query.toLowerCase()).get()
                .addOnSuccessListener { document ->
                    val data = document.documents
                    for (d in data) {
                        user = Utente(
                            d.data?.get("nome").toString(),
                            d.data?.get("cognome").toString(),
                            d.data?.get("email").toString(),
                            d.data?.get("telefono").toString(),
                            d.data?.get("password").toString()
                        )
                        users.add(user)
                    }
                    myCallBack.onCallback(users)
                }

        }


//-----------------------------------------------------------------------------------------------------------------------


//---RICERCA UTENTI per cognome -----------------------------------


        fun SearchUsersBySurname(query: String, myCallBack: MyCallbackFoundUsers) {
            var users: ArrayList<Utente> = ArrayList<Utente>()
            var user: Utente

            myRef.collection("users").whereEqualTo("cognome", query.toLowerCase()).get()
                .addOnSuccessListener { document ->
                    val data = document.documents
                    for (d in data) {

                        user = Utente(
                            d.data?.get("nome").toString(),
                            d.data?.get("cognome").toString(),
                            d.data?.get("email").toString(),
                            d.data?.get("telefono").toString(),
                            d.data?.get("password").toString()
                        )
                        users.add(user)
                    }
                    myCallBack.onCallback(users)
                }

        }


//-----------------------------------------------------------------------
        //-----------Ricerca per nome e cognome---------------------------------------------------

        fun SearchUsersByNameANDSurname(
            queryName: String,
            querySurname: String,
            myCallBack: MyCallbackFoundUsers
        ) {
            var users: ArrayList<Utente> = ArrayList<Utente>()
            var user: Utente



            myRef.collection("users").whereEqualTo("nome", queryName.toLowerCase())
                .whereEqualTo("cognome", querySurname.toLowerCase()).get()
                .addOnSuccessListener { document ->
                    val data = document.documents
                    for (d in data) {

                        user = Utente(
                            d.data?.get("nome").toString(),
                            d.data?.get("cognome").toString(),
                            d.data?.get("email").toString(),
                            d.data?.get("telefono").toString(),
                            d.data?.get("password").toString()
                        )
                        users.add(user)
                    }
                    myCallBack.onCallback(users)
                }

        }



//------AGGIORNAMENTO UTENTE DATO USERNAME ---------------//

        //Ricerca utente per email
        fun SearchUsersByEmail(email: String, myCallBack: MyCallbackFoundUser) {

            myRef.collection("users").document(email).get()
                .addOnSuccessListener { document ->
                    myCallBack.onCallback(Utente(
                        document.data?.get("nome").toString(),
                        document.data?.get("cognome").toString(),
                        document.data?.get("email").toString(),
                        document.data?.get("telefono").toString(),
                        document.data?.get("password").toString()
                    ))
                }

        }



        fun updateUserByEmail(
            name: String,
            surname: String,
            email: String,
            number: String,
            password: String
        ) {
            var user = myRef.collection("users").document(email!!)
            val nomeSafe = name.replace(" ","")
            val cognomeSafe = surname.replace(" ","")
            user.update(
                "nome" , nomeSafe.toLowerCase(),
                "cognome", cognomeSafe.toLowerCase(),
                "email", email,
                "telefono", number,
                "password", password
            ).addOnSuccessListener {
                //Aggiorno i campi nell'Auth Handler
                Auth_Handler.CURRENT_USER = Utente(
                    name, surname,
                    Auth_Handler.CURRENT_USER!!.email, number, password
                )
                //Aggiorno i campi dell'Auth firebase
                var firebaseuser = Firebase.auth.currentUser
                if (firebaseuser != null) {
                    firebaseuser.updatePassword(password)
                }


            }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error updating document", e)
                }

        }

        //FUNZIONE PER L'AGGIUNTA DI UN NUOVO UTENTE
        fun newUser(
            password: String,
            name: String,
            surname: String,
            email: String,
            telefono: String,
        ) {
            val nomeSafe = name.replace(" ".toRegex(),"")
            val cognomeSafe = surname.replace(" ".toRegex(),"")
            val docData = hashMapOf(
                "password" to password,
                "nome" to nomeSafe.toLowerCase(),
                "cognome" to cognomeSafe.toLowerCase(),
                "email" to email,
                "telefono" to telefono
            )

            myRef.collection("users").document(email)
                .set(docData)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        }

        fun getReservationList(email: String, myCallBack: MyCallbackReservations) {

            myRef.collection("users").document(email).collection("prenotazioni").get()
                .addOnSuccessListener {
                    val data = it.documents
                    val reservations = ArrayList<Prenotazione>()
                    if (data == null) {
                        myCallBack.onCallback(null)
                    } else {

                        for (reservation in data) {
                            var prenotatore: DocumentReference =
                                reservation.data!!.get("prenotatore") as DocumentReference
                            var timestamp =
                                reservation?.get("oraInizio") as com.google.firebase.Timestamp
                            var milliseconds =
                                timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
                            val dtInizio = Date(milliseconds)
                            timestamp =
                                reservation?.get("oraFine") as com.google.firebase.Timestamp
                            milliseconds =
                                timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
                            val dtFine = Date(milliseconds)
                            reservations.add(
                                Prenotazione(
                                    reservation.id,
                                    prenotatore.id,
                                    dtInizio,
                                    dtFine
                                )
                            )
                        }
                        myCallBack.onCallback(reservations)
                    }

                }
        }

    }

}





