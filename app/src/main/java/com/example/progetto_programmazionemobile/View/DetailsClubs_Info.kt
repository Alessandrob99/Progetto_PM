package com.example.progetto_programmazionemobile.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.progetto_programmazionemobile.Model.Club
import com.example.progetto_programmazionemobile.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class DetailsClubs_Info : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_details_clubs__info, container, false)
        val bundle = activity?.intent?.extras
        val circolo = bundle!!.getSerializable("club") as Club

        val email : TextView =v.findViewById(R.id.txtemail)
        val docce : TextView =v.findViewById(R.id.docce)
        val telefono : TextView =v.findViewById(R.id.txtTelefonoClub)
        email.text = "Email: "+circolo.email
        telefono.text = "Telefono: "+circolo.telefono
        if(circolo.docce){
            docce.text = "Sono presenti docce/spogliatoi"
        }else{
            docce.text = "Non sono presenti docce/spogliatoi"
        }






        val viewPagerClub : ViewPager2 = v.findViewById(R.id.viewPagerClub)
        val tabDots : TabLayout = v.findViewById(R.id.tabDots)
        viewPagerClub.adapter = MyAdapterViewPager(activity,circolo)
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