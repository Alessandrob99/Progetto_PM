package com.example.progetto_programmazionemobile.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.progetto_programmazionemobile.R


class HomePage_Activity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page_)
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        return when (item?.itemId) {
            R.id.action_profile -> {
                val fra = ProfileFragment.newInstance()
                changeFragment(fra)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean
    {
        menuInflater.inflate(R.menu.home_page_toolbar_menu, menu)
        return true
    }

    fun changeFragment(frag: Fragment)
    {
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition?.replace(R.id.fragment3, frag)
        fragmentTransition?.commit()
    }

}
