package com.example.progetto_programmazionemobile.View

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
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
}