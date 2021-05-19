package com.example.progetto_programmazionemobile.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.progetto_programmazionemobile.R
import com.google.android.material.navigation.NavigationView


class HomePage_Activity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener
{
    private lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page_)

        var toolbar: Toolbar
        //var toolbarRicerca: Toolbar

        //toolbarRicerca = findViewById(R.id.toolbarRicerca)
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        //setSupportActionBar(toolbarRicerca)

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
            ChangeFragment(infoFragment())
        }

    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.nav_home ->{
                ChangeFragment(infoFragment())
            }
            R.id.nav_profilo ->{
                ChangeFragment(ProfileFragment())
            }
            R.id.nav_ricerca_giocatori ->{
                ChangeFragment(RicercaGiocatori())
            }
            R.id.nav_ricerca_circoli -> {
                ChangeFragment(RicercaCircoli())
            }
        }
        return true
    }


    fun ChangeFragment(frag: Fragment)
    {
        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(R.id.fragment_container, frag).commit()
        drawer.closeDrawer(GravityCompat.START)
    }


}


