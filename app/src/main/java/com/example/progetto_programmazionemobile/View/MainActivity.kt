 package com.example.progetto_programmazionemobile.View

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.progetto_programmazionemobile.Model.Utente
import com.example.progetto_programmazionemobile.R

 class MainActivity : AppCompatActivity() {
     lateinit var user : Utente
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



    }

     override fun onBackPressed() {
         val navController =  findNavController(R.id.fragment)
         navController.navigate(R.id.startFragment)
     }

}

