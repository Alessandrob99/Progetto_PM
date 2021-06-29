package com.example.progetto_programmazionemobile.View

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.progetto_programmazionemobile.Model.Campo
import com.example.progetto_programmazionemobile.Model.Circolo
import com.example.progetto_programmazionemobile.R
import com.example.progetto_programmazionemobile.ViewModel.DB_Handler_Clubs
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoQueryBounds
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

@SuppressLint("MissingPermission")
class Selezione_2 : AppCompatActivity(), OnMapReadyCallback {


    lateinit var bounds : GeoQueryBounds




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selezione_2)

        //leggo la mia posizione qui....metterla prima dell'on create causa il crash dell'applicazione
        val locMan : LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val myLocation : Location = locMan.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        val myLng = myLocation.longitude
        val myLat = myLocation.latitude


        //RIFERIMETI AL TASTO DI AGGIORNAMENTO
        val aggiornaFiltriButton : Button = findViewById(R.id.aggiornFiltriButton)



        /*          VECCHIA IMPLEMENTAZIONE
        //Leggo tutti i circoli
        DB_Handler_Clubs.getAllClubsInRange(object : DB_Handler_Clubs.MyCallbackClubs {
            override fun onCallback(returnedClubs: ArrayList<Circolo>?) {
                //Assegno alla mia variabile locale i circoli trovati
                if (returnedClubs != null) {
                    //se abbiamo trovato circoli li leggiamo senno' no
                    found_clubs = returnedClubs
                    //Mostro la mappa solo dopo aver letto da Firestore i circoli
                    val mapFragment =
                    supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
                    mapFragment?.getMapAsync(this@Selezione_2)
                } else {
                    val mapFragment =
                    supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
                    mapFragment?.getMapAsync(this@Selezione_2)
                }


            }
        },myLocation,25 as Float)
        */

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this@Selezione_2)


        aggiornaFiltriButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
                mapFragment?.getMapAsync(this@Selezione_2)

            }
        })

    }




    //DISEGNA LA MAPPA DI GOOGLE
    override fun onMapReady(googleMap: GoogleMap) {
        var found_clubs : ArrayList<Circolo>? = null

        //leggo la mia posizione qui....metterla prima dell'on create causa il crash dell'applicazione
        val locMan : LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val myLocation : Location = locMan.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        val myLng = myLocation.longitude
        val myLat = myLocation.latitude

        //DEFINISCO UNA MAPPA DI TUTTI I MARKER TROVATI IN QUANTO POI DEVO DEFINIRE
        //UN COMPORTAMENTO DIVERSO PER OGNI CLICK SUL MARKER
        var markers : MutableMap<String,Marker> = mutableMapOf()

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
        Circolo.filterClubs(myLocation,raggioInKm,riscaldamentoCheck,docceCheck,copertoCheck,superficie,prezzoMax,object : Circolo.MyCallbackClubs{
            override fun onCallback(returnedCourts: ArrayList<Circolo>?) {
                found_clubs = returnedCourts

                if(found_clubs!=null) {
                    for(club in found_clubs!!){
                        //Aggiungo marker del club sulla mappa
                        marker = googleMap.addMarker(
                            MarkerOptions().position(
                                LatLng(
                                    club.posizione[0],
                                    club.posizione[1]
                                )
                            ).title(club.nome)
                        )
                        markers.put(marker.position.toString(),marker)
                    }

                    googleMap.setOnInfoWindowClickListener(object : GoogleMap.OnInfoWindowClickListener{

                        override fun onInfoWindowClick(p0: Marker) {
                            //Oggetto che rappresenta il marker cliccato
                            val clickedMarker = markers.get(p0.position.toString())

                            Toast.makeText(this@Selezione_2,"Hai cliccato "+clickedMarker?.title,Toast.LENGTH_SHORT).show()
                        }


                    })
                }else{
                    //Nessun campo trovato dopo il filtraggio
                    val builder : AlertDialog.Builder = AlertDialog.Builder(this@Selezione_2)
                    builder.setTitle("Attenzione!")
                    builder.setMessage("Nessun campo soddisfa le caratteristiche desiderate")
                    builder.setPositiveButton("OK",object : DialogInterface.OnClickListener{
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            //Click sull'avviso di nessun circolo trovato
                        }
                    })
                    val alertDialog = builder.create()
                    alertDialog.show()
                }


                //ZOOM sulla posizione attuale
                val cameraPosition = CameraPosition.Builder().target(LatLng(myLat, myLng)).zoom(10.0f).build()
                val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
                googleMap.moveCamera(cameraUpdate)



            }
        })



    }
}