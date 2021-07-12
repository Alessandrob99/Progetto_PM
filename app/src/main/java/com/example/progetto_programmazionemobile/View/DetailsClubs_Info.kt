package com.example.progetto_programmazionemobile.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.progetto_programmazionemobile.Model.Campo
import com.example.progetto_programmazionemobile.Model.Circolo
import com.example.progetto_programmazionemobile.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.ArrayList


class DetailsClubs_Info : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_details_clubs__info, container, false)
        val bundle = activity?.intent?.extras
        val circolo = bundle!!.getSerializable("club") as Circolo

        val email : TextView =v.findViewById(R.id.txtemail)
        val telefono : TextView =v.findViewById(R.id.txtTelefonoClub)
        email.text = "Email: "+circolo.email
        telefono.text = "Telefono: "+circolo.telefono





        val viewPagerClub : ViewPager2 = v.findViewById(R.id.viewPagerClub)
        val tabDots : TabLayout = v.findViewById(R.id.tabDots)
        viewPagerClub.adapter = MyAdapterViewPager(activity)
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