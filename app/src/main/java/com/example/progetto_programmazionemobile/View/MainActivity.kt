 package com.example.progetto_programmazionemobile.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.progetto_programmazionemobile.Model.Utente
import com.example.progetto_programmazionemobile.R
import com.example.progetto_programmazionemobile.ViewModel.DB_Handler
import com.google.firebase.firestore.FirebaseFirestore

 class MainActivity : AppCompatActivity() {
     lateinit var user : Utente
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val db_conn = DB_Handler()


        //LETTURA
   /*     val userReturn =  db_conn.readUser("2",object : DB_Handler.MyCallback{
            override fun onCallback(returnValue: Utente) {
                Toast.makeText(this@MainActivity,returnValue.nome.toString(),Toast.LENGTH_LONG).show()
            }
        })

*/





        //SCRITTURA
     //   db_conn.writeUser("Leonardo","Ciuccio",30,"3")

    }

}

