package com.example.progetto_programmazionemobile.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.progetto_programmazionemobile.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_details_clubs.*


class DetailsClubs_Info : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_details_clubs__info, container, false)
        init()


        return v
    }

    private fun init(){

        //viewPagerClub.adapter = MyAdapterViewPager(context)
        TabLayoutMediator(tabDots, viewPagerClub){ tab, position ->
        }.attach()
        viewPagerClub.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })
    }

}