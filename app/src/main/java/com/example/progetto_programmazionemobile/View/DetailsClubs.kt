package com.example.progetto_programmazionemobile.View

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.progetto_programmazionemobile.Model.Court
import com.example.progetto_programmazionemobile.Model.Club
import com.example.progetto_programmazionemobile.R
import com.google.android.material.tabs.TabLayout
import java.util.*


class DetailsClubs:AppCompatActivity(){

    val manager = supportFragmentManager
    var viewPager: ViewPager? = null
    var adapter: PickerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val club = intent.getSerializableExtra("club") as Club


        val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(arg0: Context?, intent: Intent) {
                val action = intent.action
                if (action == "finish_activity") {
                    finish()
                    // DO WHATEVER YOU WANT.
                }
            }
        }
        registerReceiver(broadcastReceiver, IntentFilter("logout"))

        val broadcastReceiver1: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(arg0: Context?, intent: Intent) {
                val action = intent.action
                if (action == "logout") {
                    finish()
                    // DO WHATEVER YOU WANT.
                }
            }
        }
        registerReceiver(broadcastReceiver1, IntentFilter("logout"))


        setContentView(R.layout.activity_details_clubs)
        val titleClub = findViewById<TextView>(R.id.titleClub)
        titleClub.text = club.nome

        adapter = PickerAdapter(supportFragmentManager)
        viewPager = findViewById<ViewPager>(R.id.pager)
        viewPager?.setAdapter(adapter)

        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)
        for (i in 0 until adapter?.getCount()!!) tabLayout.getTabAt(i)?.setText(adapter!!.getTitle(i))


    }





    class PickerAdapter internal constructor(fm: FragmentManager?) :
        FragmentPagerAdapter(fm!!) {
        var Campi: Fragment
        var Info: Fragment
        override fun getCount(): Int {
            return NUM_PAGES
        }

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> Campi
                1 -> Info
                else -> Info
            }
        }

        fun getTitle(position: Int): Int {
            return when (position) {
                0 -> R.string.titleCampi
                1 -> R.string.titleInfo
                else -> R.string.title
            }
        }

        companion object {
            private const val NUM_PAGES = 2
        }

        init {
            Campi = DetailsClubs_Campi()
            Info = DetailsClubs_Info()
        }
    }


}








