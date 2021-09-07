package com.example.progetto_programmazionemobile.View

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.progetto_programmazionemobile.Model.Club
import com.example.progetto_programmazionemobile.R
import com.example.progetto_programmazionemobile.ViewModel.DB_Handler_Clubs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class SearchClub : Fragment() , OnMapReadyCallback {
    lateinit var progress : ProgressDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val v =  inflater.inflate(R.layout.fragment_ricerca_circoli, container, false)
        progress = ProgressDialog(context)
        val supportFragmentManager : SupportMapFragment = SupportMapFragment.newInstance()
        childFragmentManager.beginTransaction().replace(R.id.mapCircoli,supportFragmentManager).commit()
        supportFragmentManager?.getMapAsync(this)
        progress.setTitle("Caricamento...")
        progress.show()

        return v
    }

    override fun onMapReady(googleMap: GoogleMap) {

        DB_Handler_Clubs.getAllClubs(object : DB_Handler_Clubs.MyCallbackClubs{
            override fun onCallback(returnedClubs: ArrayList<Club>?) {
                if (returnedClubs != null) {
                    for (club in returnedClubs){
                        var marker = googleMap.addMarker(
                            MarkerOptions().position(
                                LatLng(
                                    club.posizione[0],
                                    club.posizione[1]
                                )
                            ).title(club.nome)
                        )
                    }
                    progress.dismiss()
                    val cameraPosition =
                        CameraPosition.Builder().target(LatLng(42.54701640764917, 12.4)).zoom(
                            5.5f
                        ).build()
                    val cameraUpdate = CameraUpdateFactory.newCameraPosition(
                        cameraPosition
                    )
                    googleMap.moveCamera(cameraUpdate)
                }
            }
        })



    }

}
