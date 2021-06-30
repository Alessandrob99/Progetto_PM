package com.example.progetto_programmazionemobile.View

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.location.LocationManagerCompat.isLocationEnabled
import com.example.progetto_programmazionemobile.Model.Circolo
import com.example.progetto_programmazionemobile.R
import com.firebase.geofire.GeoQueryBounds
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*


@SuppressLint("MissingPermission")

class Selezione_2 : AppCompatActivity(), OnMapReadyCallback {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selezione_2)

        /*
        VAL CAMPI_PER_ORA = INTETN.GETEXTRA("CAMPI_FILTRATI")
        */

        //RIFERIMETI AL TASTO DI AGGIORNAMENTO
        val aggiornaFiltriButton : Button = findViewById(R.id.aggiornFiltriButton)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this@Selezione_2)


        aggiornaFiltriButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
                mapFragment?.getMapAsync(this@Selezione_2)

            }
        })

    }




    //DISEGNA LA MAPPA DI GOOGLE
    override fun onMapReady(googleMap: GoogleMap) {

        //Location Manager
        val locMan : LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (isLocationEnabled(locMan)) {
            var found_clubs : ArrayList<Circolo>? = null

            //Pulisco la mappa dai vecchi marker prima di ricaricarla
            googleMap.clear()

            //Lettura della posizione
            getLocation(object : MyCallbackPosition{
                override fun onCallback(position: Location?) {
                    //Posizione Pronta
                    //CONTROLLO SE LA POSIZIONE LETTA è NULL
                    var myLocation = position

                    if(myLocation != null){
                        var myLat : Double = myLocation.latitude
                        var myLng : Double = myLocation.longitude
                        //DEFINISCO UNA MAPPA DI TUTTI I MARKER TROVATI IN QUANTO POI DEVO DEFINIRE
                        //UN COMPORTAMENTO DIVERSO PER OGNI CLICK SUL MARKER
                        var markers : MutableMap<String, Marker> = mutableMapOf()

                        googleMap.addMarker(
                            MarkerOptions().position(LatLng(myLat, myLng)).title("Tu sei qui!").icon(
                                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
                            )
                        )
                        var marker : Marker
                        //LEGGE I VALORI E APPLICA FILTRI
                        val riscaldamentoCheck : Boolean = findViewById<CheckBox>(R.id.riscaldamentoCheckbox).isChecked
                        val docceCheck : Boolean = findViewById<CheckBox>(R.id.docceCheckbox).isChecked
                        val copertoCheck : Boolean = findViewById<CheckBox>(R.id.copertoCheckbox).isChecked
                        val raggioInKm : Float? = findViewById<EditText>(R.id.radiusMax).text.toString().toFloatOrNull()
                        val superficie : String = findViewById<EditText>(R.id.superficie).text.toString()
                        val prezzoMax : Float?  = findViewById<EditText>(R.id.prezzoMax).text.toString().toFloatOrNull()

                        //FUNZIONE CHE PRENDE L'ELENCO DEI CIRCOLI E APPLICA I FILTRI
                        Circolo.filterClubs(
                            myLocation!!,
                            raggioInKm,
                            riscaldamentoCheck,
                            docceCheck,
                            copertoCheck,
                            superficie,
                            prezzoMax,
                            object : Circolo.MyCallbackClubs {
                                override fun onCallback(returnedCourts: ArrayList<Circolo>?) {
                                    found_clubs = returnedCourts

                                    if (found_clubs!!.size > 0) {
                                        for (club in found_clubs!!) {
                                            //Aggiungo marker del club sulla mappa
                                            marker = googleMap.addMarker(
                                                MarkerOptions().position(
                                                    LatLng(
                                                        club.posizione[0],
                                                        club.posizione[1]
                                                    )
                                                ).title(club.nome)
                                            )
                                            markers.put(marker.position.toString(), marker)
                                        }

                                        googleMap.setOnInfoWindowClickListener(object :
                                            GoogleMap.OnInfoWindowClickListener {

                                            override fun onInfoWindowClick(p0: Marker) {
                                                //Oggetto che rappresenta il marker cliccato
                                                val clickedMarker = markers.get(p0.position.toString())
                                                //Cosa fare quando si clicca sul marker
                                                Toast.makeText(
                                                    this@Selezione_2,
                                                    "Hai cliccato " + clickedMarker?.title,
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        })
                                    } else {
                                        //Nessun campo trovato dopo il filtraggio
                                        val builder: AlertDialog.Builder = AlertDialog.Builder(this@Selezione_2)
                                        builder.setTitle("Attenzione!")
                                        builder.setMessage("Nessun campo soddisfa le caratteristiche desiderate")
                                        builder.setPositiveButton(
                                            "OK",
                                            object : DialogInterface.OnClickListener {
                                                override fun onClick(dialog: DialogInterface?, which: Int) {
                                                    //Click sull'avviso di nessun circolo trovato
                                                }
                                            })
                                        val alertDialog = builder.create()
                                        alertDialog.show()
                                    }

                                    //ZOOM sulla posizione attuale
                                    val cameraPosition =
                                        CameraPosition.Builder().target(LatLng(myLat, myLng)).zoom(
                                            10.0f
                                        ).build()
                                    val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
                                    googleMap.moveCamera(cameraUpdate)


                                }
                            })
                    }else{
                        //Errore in lettura della posizione
                        val builder: AlertDialog.Builder = AlertDialog.Builder(this@Selezione_2)
                        builder.setTitle("Errore!")
                        builder.setMessage("Errore durante la lettura della posizione")
                        builder.setPositiveButton(
                            "OK",
                            object : DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface?, which: Int) {
                                    val goToSelection = Intent(applicationContext, Selezione_1::class.java)
                                    startActivity(goToSelection)
                                    finish()
                                }
                            })
                        val alertDialog = builder.create()
                        alertDialog.show()
                    }
                }
            })

        }else {
            val builder : AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("ATTENZIONE")
            builder.setMessage("E' necessario abilitare la geolocalizzazione per accedere a questa funzionalità")
            builder.setPositiveButton("OK", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    val goToSelection = Intent(applicationContext, Selezione_1::class.java)
                    startActivity(goToSelection)
                    finish()
                }
            })
            val alertDialog = builder.create()
            alertDialog.show()
        }

    }

    //Call Back per la lettura della posizione che necessita di tempo
    interface MyCallbackPosition{
        fun onCallback(position: Location?)
    }

    fun getLocation(myCallbackPosition: MyCallbackPosition) {
        val locationManager : LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val gpsOn = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val netOn = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        var netLocation : Location? = null
        var gpsLocation : Location? = null

        if(gpsOn || netOn){
            if(gpsOn){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,100000,0F,object : LocationListener{
                    override fun onLocationChanged(location: Location?) {
                        if(location!=null){
                            gpsLocation = location
                        }
                    }

                    override fun onProviderDisabled(provider: String?) {
                    }

                    override fun onProviderEnabled(provider: String?) {
                    }

                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

                    }
                })
                var localLocationVar = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if(localLocationVar!=null){
                    netLocation = localLocationVar
                }
            }
            if(netOn){
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,100000,0F,object : LocationListener{
                    override fun onLocationChanged(location: Location?) {
                        if(location!=null){
                            netLocation = location
                        }
                    }

                    override fun onProviderDisabled(provider: String?) {
                    }

                    override fun onProviderEnabled(provider: String?) {
                    }

                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

                    }
                })
                var localLocationVar = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                if(localLocationVar!=null){
                    gpsLocation = localLocationVar
                }
            }
        }

        if(gpsLocation!=null && netLocation!=null){
            if(gpsLocation!!.accuracy>netLocation!!.accuracy){
                myCallbackPosition.onCallback(gpsLocation!!)
            }else{
                myCallbackPosition.onCallback(netLocation!!)
            }
        }else{
            if(gpsLocation==null && netLocation==null){
                myCallbackPosition.onCallback(null)

            }else{
                if(netLocation!=null){
                    myCallbackPosition.onCallback(netLocation!!)
                }else{
                    myCallbackPosition.onCallback(gpsLocation!!)
                }
            }

        }

    }
}
