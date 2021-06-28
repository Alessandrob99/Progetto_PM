package com.example.progetto_programmazionemobile.View

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.progetto_programmazionemobile.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class Selezione_2 : AppCompatActivity(), OnMapReadyCallback {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selezione_2)

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment


        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED) {

                //PERMESSO GARANTITO
                mapFragment?.getMapAsync(this)

        } else {
            //RICHIESTA PERMESSO DI ACCESO ALLA POSIZIONE
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                10
            )
            mapFragment?.getMapAsync(this)

        }

    }
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        val locMan : LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val myLocation : Location = locMan.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        val myLng = myLocation.longitude
        val myLat = myLocation.latitude

        //Possibile usarlo in un for loop ^^^
        /*var positions = mutableListOf<LatLng>()
        positions.add(LatLng(myLat,myLng))
        Toast.makeText(applicationContext,positions.size.toString(),Toast.LENGTH_SHORT).show()*/

        googleMap.addMarker(
            MarkerOptions().position(LatLng(myLat, myLng)).title("Tu sei qui!")
        )

        val cameraPosition = CameraPosition.Builder().target(LatLng(myLat,myLng)).zoom(14.0f).build()
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        googleMap.moveCamera(cameraUpdate)
    }
}