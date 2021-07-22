package com.example.progetto_programmazionemobile.View

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.example.progetto_programmazionemobile.Model.Circolo
import com.example.progetto_programmazionemobile.R
import com.example.progetto_programmazionemobile.ViewModel.Auth_Handler
import com.example.progetto_programmazionemobile.ViewModel.DB_Handler_Clubs
import com.google.android.material.navigation.NavigationView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_home_profile_fragment.*
import org.w3c.dom.Text


class HomePage_Activity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener
{
    private lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page_)


        val window: Window = this.getWindow()
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.purple_700))

        var toolbar: Toolbar
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)

        var navigationView : NavigationView
        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        var toggle: ActionBarDrawerToggle
        toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawer.addDrawerListener(toggle)
        toggle.syncState()
        if(savedInstanceState == null)
        {
            ChangeFragment(infoFragment(),"HOME")
        }
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val picRef : StorageReference? = storageRef.child("usersPics/"+Auth_Handler.CURRENT_USER!!.email)
        if(picRef!=null){
            picRef.downloadUrl.addOnSuccessListener{
                Glide.with(this).load(it).into(drawer.findViewById(R.id.ImgProfiloDrawer))
            }
        }

        val navView : NavigationView = findViewById(R.id.nav_view)
        val header = navView.getHeaderView(0)
        header.findViewById<TextView>(R.id.NameDrawer).text = Auth_Handler.CURRENT_USER!!.nome.capitalize()
        header.findViewById<TextView>(R.id.SurnameDrawer).text = Auth_Handler.CURRENT_USER!!.cognome.capitalize()
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {

            if(supportFragmentManager.findFragmentByTag("HOME")!=null){
                if(supportFragmentManager.findFragmentByTag("HOME")!!.isVisible) {
                    val builder : AlertDialog.Builder = AlertDialog.Builder(this)
                    builder.setTitle("Selezionare si o no")
                    builder.setMessage("Effettuare il logout?")

                    builder.setNegativeButton("NO",object : DialogInterface.OnClickListener{
                        override fun onClick(dialog: DialogInterface?, which: Int) {

                        }
                    })
                    builder.setPositiveButton("SI",object : DialogInterface.OnClickListener{
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            Auth_Handler.setLOGGET_OUT(context = applicationContext)
                            val broadcastIntent = Intent()
                            broadcastIntent.action = "logout"
                            sendBroadcast(broadcastIntent)
                            val intent = Intent(this@HomePage_Activity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    })
                    val alertDialog = builder.create()
                    alertDialog.show()
                }
            }
            if(supportFragmentManager.findFragmentByTag("PROFILO")!=null){
                if(supportFragmentManager.findFragmentByTag("PROFILO")!!.isVisible) {
                    ChangeFragment(infoFragment(),"HOME")
                }
            }
            if(supportFragmentManager.findFragmentByTag("RICERCA_GIOCATORI")!=null){
                if(supportFragmentManager.findFragmentByTag("RICERCA_GIOCATORI")!!.isVisible) {
                    ChangeFragment(infoFragment(),"HOME")
                }
            }
            if(supportFragmentManager.findFragmentByTag("RICERCA_CIRCOLI")!=null){
                if(supportFragmentManager.findFragmentByTag("RICERCA_CIRCOLI")!!.isVisible) {
                    ChangeFragment(infoFragment(),"HOME")
                }
            }
            if(supportFragmentManager.findFragmentByTag("MODIFICA_PROFILO")!=null){
                if(supportFragmentManager.findFragmentByTag("MODIFICA_PROFILO")!!.isVisible) {
                    ChangeFragment(ProfileFragment(),"PROFILO")
                }
            }
            if(supportFragmentManager.findFragmentByTag("APP")!=null){
                if(supportFragmentManager.findFragmentByTag("APP")!!.isVisible) {
                    ChangeFragment(infoFragment(),"HOME")
                }
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.nav_home ->{
                ChangeFragment(infoFragment(),"HOME")
            }
            R.id.nav_profilo ->{
                ChangeFragment(ProfileFragment(),"PROFILO")
            }
            R.id.nav_ricerca_giocatori ->{
                ChangeFragment(RicercaGiocatori(),"RICERCA_GIOCATORI")
            }
            R.id.nav_ricerca_circoli -> {
                ChangeFragment(RicercaCircoli(),"RICERCA_CIRCOLI")
            }
            R.id.info_app -> {
                ChangeFragment(InfoApp(), "APP")
            }
        }
        return true
    }


    fun ChangeFragment(frag: Fragment, ID : String)
    {
        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(R.id.fragment_container, frag,ID).commit()

        val navView : NavigationView = findViewById(R.id.nav_view)
        val header = navView.getHeaderView(0)
        header.findViewById<TextView>(R.id.NameDrawer).text = Auth_Handler.CURRENT_USER!!.nome.capitalize()
        header.findViewById<TextView>(R.id.SurnameDrawer).text = Auth_Handler.CURRENT_USER!!.cognome.capitalize()

        drawer.closeDrawer(GravityCompat.START)
    }



}


