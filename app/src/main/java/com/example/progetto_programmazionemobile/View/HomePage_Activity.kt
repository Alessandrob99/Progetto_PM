package com.example.progetto_programmazionemobile.View

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.progetto_programmazionemobile.R
import com.google.android.material.navigation.NavigationView


class HomePage_Activity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener
{
    private lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page_)
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
            ChangeFragment(infoFragment(),"INFO")
        }

    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {

            if(supportFragmentManager.findFragmentByTag("INFO")!=null){
                if(supportFragmentManager.findFragmentByTag("INFO")!!.isVisible) {
                    val intent = Intent(this, LogoutPopUp::class.java)
                    startActivity(intent)
                }
            }
            if(supportFragmentManager.findFragmentByTag("PROFILO")!=null){
                if(supportFragmentManager.findFragmentByTag("PROFILO")!!.isVisible) {
                    ChangeFragment(infoFragment(),"INFO")
                }
            }
            if(supportFragmentManager.findFragmentByTag("RICERCA_GIOCATORI")!=null){
                if(supportFragmentManager.findFragmentByTag("RICERCA_GIOCATORI")!!.isVisible) {
                    ChangeFragment(infoFragment(),"INFO")
                }
            }
            if(supportFragmentManager.findFragmentByTag("RICERCA_CIRCOLI")!=null){
                if(supportFragmentManager.findFragmentByTag("RICERCA_CIRCOLI")!!.isVisible) {
                    ChangeFragment(infoFragment(),"INFO")
                }
            }
            if(supportFragmentManager.findFragmentByTag("MODIFICA_PROFILO")!=null){
                if(supportFragmentManager.findFragmentByTag("MODIFICA_PROFILO")!!.isVisible) {
                    ChangeFragment(ProfileFragment(),"PROFILO")
                }
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.nav_home ->{
                ChangeFragment(infoFragment(),"INFO")
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
        }
        return true
    }


    fun ChangeFragment(frag: Fragment, ID : String)
    {
        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(R.id.fragment_container, frag,ID).commit()
        drawer.closeDrawer(GravityCompat.START)
    }



}


