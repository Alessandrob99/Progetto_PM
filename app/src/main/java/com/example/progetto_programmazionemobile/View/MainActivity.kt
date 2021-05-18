 package com.example.progetto_programmazionemobile.View

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.progetto_programmazionemobile.Model.Utente
import com.example.progetto_programmazionemobile.R
import com.example.progetto_programmazionemobile.ViewModel.DB_Handler

 class MainActivity : AppCompatActivity() {
     lateinit var user : Utente
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



       // val db_conn = DB_Handler()   Connessione al DB


      //  LETTURA
       /*val userReturn =  db_conn.readUser("2",object : DB_Handler.MyCallbackUser{
            override fun onCallback(returnValue: Utente) {
                Toast.makeText(this@MainActivity,returnValue.nome.toString(),Toast.LENGTH_LONG).show()
            }
        }


*/





        //SCRITTURA
     //   db_conn.writeUser("Leonardo","Ciuccio",30,"3")

    }

}

