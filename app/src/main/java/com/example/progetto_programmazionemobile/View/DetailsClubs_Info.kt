package com.example.progetto_programmazionemobile.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.progetto_programmazionemobile.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class DetailsClubs_Info : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_details_clubs__info, container, false)


        /*val email : TextView = findViewById(R.id.txtemail_Club)
       email.text = "Email: "+club.email
       val telefono : TextView = findViewById(R.id.txtTelefono_Club)
       telefono.text = "Telefono: "+club.telefono
       val rv: RecyclerView = findViewById(R.id.recyclearCampi)
       rv.layoutManager = LinearLayoutManager(this)
       rv.adapter = MyAdapterCourts(courts)

        */

        val viewPagerClub : ViewPager2 = v.findViewById(R.id.viewPagerClub)
        val tabDots : TabLayout = v.findViewById(R.id.tabDots)
        viewPagerClub.adapter = MyAdapterViewPager(activity)
        //viewPagerClub.adapter = MyAdapterViewPager(context)
        TabLayoutMediator(tabDots, viewPagerClub){ tab, position ->
        }.attach()
        viewPagerClub.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })


        return v
    }





}