package com.example.progetto_programmazionemobile.View

import android.content.pm.ActivityInfo
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.graphics.drawable.DrawableCompat
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

    /*override fun onOptionsItemSelected(item: MenuItem)
    {
        R.id.action_profile -> { Start

    }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }*/

}