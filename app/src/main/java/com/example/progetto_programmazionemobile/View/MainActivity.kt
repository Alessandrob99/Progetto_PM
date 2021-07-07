 package com.example.progetto_programmazionemobile.View

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.example.progetto_programmazionemobile.BuildConfig
import com.example.progetto_programmazionemobile.Model.Campo
import com.example.progetto_programmazionemobile.Model.Prenotazione
import com.example.progetto_programmazionemobile.Model.Utente
import com.example.progetto_programmazionemobile.R
import com.example.progetto_programmazionemobile.ViewModel.DB_Handler_Courts
import com.example.progetto_programmazionemobile.ViewModel.DB_Handler_Reservation
import com.google.android.material.dialog.MaterialAlertDialogBuilder

 class MainActivity : AppCompatActivity() {
     lateinit var user : Utente
     @RequiresApi(Build.VERSION_CODES.O)
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


         if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
             //PERMESSO GARANTITO

         }else{
             requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),10)
         }

     }

     override fun onRequestPermissionsResult(
         requestCode: Int,
         permissions: Array<out String>,
         grantResults: IntArray
     ) {
         super.onRequestPermissionsResult(requestCode, permissions, grantResults)
         if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED){
             //RICHIESTA PERMESSO DI ACCESO ALLA POSIZIONE
             //Informiamo l'utente che l'app ha bisogno della posizione
             //POPUP Registrazione compleatta + redirect alla mainactivity
             val builder : AlertDialog.Builder = AlertDialog.Builder(this)
             builder.setTitle("NOTA BENE")
             builder.setMessage("L'utilizzo dell'app necessita dell'autorizzazione da parte dell'utente per accedere alla geolocalizzazione." +
                     System.lineSeparator()+System.lineSeparator()+"E' possibile fornirla dalle impostazioni del dispositivo")
             builder.setPositiveButton("OK",object : DialogInterface.OnClickListener{
                 override fun onClick(dialog: DialogInterface?, which: Int) {

                 }
             })
             val alertDialog = builder.create()
             alertDialog.show()
         }
     }


     override fun onBackPressed() {
         val navController =  findNavController(R.id.fragment)
         navController.navigate(R.id.startFragment)
     }

}

