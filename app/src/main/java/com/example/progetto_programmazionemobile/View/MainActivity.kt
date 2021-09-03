 package com.example.progetto_programmazionemobile.View

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.findNavController
import androidx.viewpager.widget.ViewPager
import com.example.progetto_programmazionemobile.BuildConfig
import com.example.progetto_programmazionemobile.Model.Campo
import com.example.progetto_programmazionemobile.Model.Prenotazione
import com.example.progetto_programmazionemobile.Model.Utente
import com.example.progetto_programmazionemobile.R
import com.example.progetto_programmazionemobile.ViewModel.DB_Handler_Courts
import com.example.progetto_programmazionemobile.ViewModel.DB_Handler_Reservation
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout

 class MainActivity : AppCompatActivity() {
     lateinit var user : Utente
     var viewPager: ViewPager? = null
     var adapter: PickerAdapter? = null

     @RequiresApi(Build.VERSION_CODES.O)
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         /**
          * Permesso per l'utilizzo del GPS
          */
         if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
             //PERMESSO GARANTITO

         }else{
             requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),10)
         }


         /**
          * Settaggio della barra sopra al cellulare (aspetto puramente estetico)
          */
         val window: Window = this.getWindow()
         window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
         window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
         window.setStatusBarColor(ContextCompat.getColor(this, R.color.purple_700))


         /**
          * Settaggio del viewPager per switchare il login o registrati
          */
         adapter = PickerAdapter(supportFragmentManager)
         viewPager = findViewById<ViewPager>(R.id.pagerHome)
         viewPager?.setAdapter(adapter)

         val tabLayout = findViewById<TabLayout>(R.id.tabsHome)
         tabLayout.setupWithViewPager(viewPager)
         for (i in 0 until adapter?.getCount()!!) tabLayout.getTabAt(i)?.setText(adapter!!.getTitle(i))




     }

     /**
      * RICHIESTA PERMESSO DI ACCESO ALLA POSIZIONE
        Informiamo l'utente che l'app ha bisogno della posizione
        POPUP Registrazione completata + redirect alla mainactivity
      */
     override fun onRequestPermissionsResult(
         requestCode: Int,
         permissions: Array<out String>,
         grantResults: IntArray
     ) {
         super.onRequestPermissionsResult(requestCode, permissions, grantResults)
         if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED){
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






     class PickerAdapter internal constructor(fm: FragmentManager?) :
         FragmentPagerAdapter(fm!!) {
         var Login: Fragment
         var Register: Fragment
         override fun getCount(): Int {
             return NUM_PAGES
         }

         override fun getItem(position: Int): Fragment {
             return when (position) {
                 0 -> Login
                 1 -> Register
                 else -> Login
             }
         }

         fun getTitle(position: Int): Int {
             return when (position) {
                 0 -> R.string.Login
                 1 -> R.string.Register
                 else -> R.string.title
             }
         }

         companion object {
             private const val NUM_PAGES = 2
         }

         init {
             Login = LoginFragment()
             Register = RegisterFragment()
         }
     }


     /**
      * BackPressed: Chiude l'applicazione
      */
     override fun onBackPressed() {
         val builder: AlertDialog.Builder =
             AlertDialog.Builder(this)
         builder.setTitle("Uscire?")
         builder.setMessage("Chiudere l'applicazione?")

         builder.setPositiveButton(
             "SI",
             object : DialogInterface.OnClickListener {
                 override fun onClick(dialog: DialogInterface?, which: Int) {
                     finishAndRemoveTask()
                 }
             })
         builder.setNegativeButton("NO",object : DialogInterface.OnClickListener{
             override fun onClick(dialog: DialogInterface?, which: Int) {

             }
         })
         val alertDialog = builder.create()
         alertDialog.show()
     }
}

