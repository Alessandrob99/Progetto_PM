package com.example.progetto_programmazionemobile.View

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.progetto_programmazionemobile.R
import com.example.progetto_programmazionemobile.ViewModel.DB_Handler_Clubs
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputEditText
import java.io.IOException


class FormCircoli : AppCompatActivity() , OnMapReadyCallback {
    var latLng: LatLng? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_circoli)




        val docce : CheckBox = findViewById(R.id.docceCheck)
        val nome : TextInputEditText = findViewById(R.id.nomeCircolo)
        val email : TextInputEditText = findViewById(R.id.emailCircolo)
        val telfono : TextInputEditText = findViewById(R.id.telefonoCircolo)
        val btn : Button = findViewById(R.id.confermaInfo)
        btn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                if((TextUtils.equals(nome.text.toString(),""))||(TextUtils.equals(email.text.toString(),""))||(TextUtils.equals(telfono.text.toString(),""))){
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this@FormCircoli)
                    builder.setTitle("Errore")
                    builder.setMessage("Fornire dei parametri validi")
                    builder.setPositiveButton("OK", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {

                        }
                    })
                    val alertDialog = builder.create()
                    alertDialog.show()
                }else{
                    //Invia la richiesta al DB
                    DB_Handler_Clubs.newRequest(
                        nome.text.toString(),
                        email.text.toString(),
                        telfono.text.toString(),
                        latLng!!.latitude,
                        latLng!!.longitude,
                        docce.isChecked,
                        object : DB_Handler_Clubs.MyCallbackRequest{

                            override fun onCallback(esito: Boolean) {
                                if(esito){
                                    Toast.makeText(this@FormCircoli,"Richiesta inviata",Toast.LENGTH_SHORT).show()
                                }else{
                                    Toast.makeText(this@FormCircoli,"Errore nell'invio della richiesta",Toast.LENGTH_SHORT).show()
                                }
                            }
                        })
                }
            }
        })

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMapReady(googleMap: GoogleMap) {
        val mMap = googleMap

        var fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 10)
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                val lat = location!!.latitude
                val lng = location!!.longitude
                latLng = LatLng(lat, lng)
                val marker = mMap.addMarker(
                    MarkerOptions().position(latLng).draggable(true).title("Tieni premuto e sposta")
                )
                marker.showInfoWindow()

                val zoomLevel = 13.0f //This goes up to 21

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel))

                // Enable the zoom controls for the map

                // Enable the zoom controls for the map
                mMap.getUiSettings().setZoomControlsEnabled(true)

                googleMap.setOnMarkerDragListener(object : OnMarkerDragListener {
                    override fun onMarkerDragStart(marker: Marker?) {}
                    override fun onMarkerDrag(marker: Marker?) {}
                    override fun onMarkerDragEnd(marker: Marker) {
                        try {
                            latLng = marker.getPosition()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                })


            }.addOnFailureListener{

            }


    }


}