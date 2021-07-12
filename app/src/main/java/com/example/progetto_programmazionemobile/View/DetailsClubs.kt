package com.example.progetto_programmazionemobile.View

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.progetto_programmazionemobile.Model.Campo
import com.example.progetto_programmazionemobile.Model.Circolo
import com.example.progetto_programmazionemobile.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_selezione_1.*
import kotlinx.android.synthetic.main.rv_campi.*
import org.w3c.dom.Text
import java.util.*


class DetailsClubs:AppCompatActivity(){

    val manager = supportFragmentManager
    var viewPager: ViewPager? = null
    var adapter: PickerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val club = intent.getSerializableExtra("club") as Circolo
        val courts = intent.getSerializableExtra("courts") as ArrayList<Campo>
        val nomeClub= intent.getStringExtra("titleClub")
        val giorno = intent.getStringExtra("giorno")



        setContentView(R.layout.activity_details_clubs)
        val titleClub = findViewById<TextView>(R.id.titleClub)
        titleClub.text = club.nome

        adapter = PickerAdapter(supportFragmentManager)
        viewPager = findViewById<ViewPager>(R.id.pager)
        viewPager?.setAdapter(adapter)


        //setSupportActionBar(findViewById(R.id.toolbar))
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








