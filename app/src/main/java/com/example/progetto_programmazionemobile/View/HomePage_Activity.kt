package com.example.progetto_programmazionemobile.View

import android.content.pm.ActivityInfo
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.graphics.drawable.DrawableCompat
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.progetto_programmazionemobile.R




class HomePage_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page_)
        setSupportActionBar(findViewById(R.id.toolbar))


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.home_page_toolbar_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {

        when (item?.itemId) {
            R.id.action_profile -> {

               // val v : View = inflater.inflate(R.layout.fragment_start, container, false)
               // v!!.findNavController().navigate(R.id.profileFragment)

                //Navigation.findNavController().navigate(R.id.profileFragment)
                //Navigation.findNavController().navigate(R.id.profileFragment)


                Toast.makeText(this, "Porcodeddio funziona solo cosi", Toast.LENGTH_LONG).show()

                return true
            }
            else -> return super.onOptionsItemSelected(item)


        }
        return true
    }
}
