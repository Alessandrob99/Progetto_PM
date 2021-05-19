package com.example.progetto_programmazionemobile.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
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
            val fragment = supportFragmentManager.beginTransaction()
            fragment.replace(R.id.fragment_container, RicercaGiocatori()).commit()
            navigationView.setCheckedItem(R.id.nav_home)
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
                val fragment = supportFragmentManager.beginTransaction()
                fragment.replace(R.id.fragment_container, RicercaGiocatori()).commit()
                drawer.closeDrawer(GravityCompat.START)
            }
            R.id.nav_profilo ->{
                val fragment = supportFragmentManager.beginTransaction()
                fragment.replace(R.id.fragment_container, ProfileFragment()).commit()
                drawer.closeDrawer(GravityCompat.START)
            }
        }
        return true
    }


}
