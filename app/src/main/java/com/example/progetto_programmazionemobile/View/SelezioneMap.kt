package com.example.progetto_programmazionemobile.View

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.core.location.LocationManagerCompat
import com.example.progetto_programmazionemobile.Model.Campo
import com.example.progetto_programmazionemobile.Model.Circolo
import com.example.progetto_programmazionemobile.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_selezionemap.*
import kotlinx.android.synthetic.main.bottom_sheet_layout.*

class SelezioneMap : AppCompatActivity(), OnMapReadyCallback
{
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    lateinit var autocompleteSuperficie: AutoCompleteTextView
    lateinit var campiPerSport: ArrayList<Campo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selezionemap)

        bottomSheetBehavior = BottomSheetBehavior.from<LinearLayout>(persistent_bottom_sheet)

        val aggiornaFiltriButton: Button = findViewById(R.id.aggiornFiltriButton)
        aggiornaFiltriButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                val mapFragment =
                    supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
                mapFragment?.getMapAsync(this@SelezioneMap)

            }
        })

        //Campi filtrati per sport da SELEZIONE 1
        campiPerSport = getIntent().getSerializableExtra("campiPerSport") as ArrayList<Campo>
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this@SelezioneMap)

        val arraySuperficie = arrayOf(
            "",
            "Cemento",
            "Erba",
            "Parque",
        )

        autocompleteSuperficie = findViewById(R.id.superficie) as AutoCompleteTextView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arraySuperficie)
        autocompleteSuperficie.setText(adapter.getItem(0).toString(), false)
        autocompleteSuperficie.setAdapter(adapter)

        autocompleteSuperficie.onItemSelectedListener =
            object : AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
                override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    TODO("Not yet implemented")
                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    Toast.makeText(applicationContext, "Prova", Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }





    }

    override fun onMapReady(googleMap: GoogleMap) {

        //La mia posizione
        var myLat: Double = intent.getDoubleExtra("latitude", 0.0)
        var myLng: Double = intent.getDoubleExtra("longitude", 0.0)

        //Location Manager
        var locMan: LocationManager? = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        googleMap.clear()

        if (locMan != null)
        {
            if (LocationManagerCompat.isLocationEnabled(locMan))
            {
                var found_clubs: ArrayList<Circolo>? = null

                if (myLat != 0.0 && myLng != 0.0)
                {
                    val myLocation = Location(LocationManager.GPS_PROVIDER)
                    myLocation!!.latitude = myLat
                    myLocation!!.longitude = myLng

                    //DEFINISCO UNA MAPPA DI TUTTI I MARKER TROVATI IN QUANTO POI DEVO DEFINIRE
                    //UN COMPORTAMENTO DIVERSO PER OGNI CLICK SUL MARKER
                    var markers: MutableMap<String, Marker> = mutableMapOf()

                    googleMap.addMarker(
                        MarkerOptions()
                            .position(LatLng(myLat, myLng))
                            .title("Tu sei qui!")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    )

                    var marker: Marker
                    //LEGGE I VALORI E APPLICA FILTRI
                    val riscaldamentoCheck: Boolean =
                        findViewById<CheckBox>(R.id.riscaldamentoCheckbox).isChecked
                    val docceCheck: Boolean =
                        findViewById<CheckBox>(R.id.docceCheckbox).isChecked
                    val copertoCheck: Boolean =
                        findViewById<CheckBox>(R.id.copertoCheckbox).isChecked
                    val raggioInKm: Float? =
                        findViewById<EditText>(R.id.radiusMax).text.toString().toFloatOrNull()
                    val superficie: String =
                        findViewById<AutoCompleteTextView>(R.id.superficie).text.toString()
                    val prezzoMax: Float? =
                        findViewById<EditText>(R.id.prezzoMax).text.toString().toFloatOrNull()



                    //FUNZIONE CHE PRENDE L'ELENCO DEI CIRCOLI E APPLICA I FILTRI
                    Circolo.filterClubs(
                        campiPerSport,
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

                                if (found_clubs!!.size > 0)
                                {
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
                                            val clickedMarker =
                                                markers.get(p0.position.toString())
                                            //Cosa fare quando si clicca sul marker

                                            val intent =
                                                Intent(
                                                    this@SelezioneMap,
                                                    DetailsClubs::class.java
                                                )
                                            intent.putExtra("titleClub", clickedMarker?.title)
                                            startActivity(intent)
                                            finish()

                                        }
                                    })
                                } else {
                                    //Nessun campo trovato dopo il filtraggio
                                    val builder: AlertDialog.Builder =
                                        AlertDialog.Builder(this@SelezioneMap)
                                    builder.setTitle("Attenzione!")
                                    builder.setMessage("Nessun campo soddisfa le caratteristiche desiderate")
                                    builder.setPositiveButton(
                                        "OK",
                                        object : DialogInterface.OnClickListener {
                                            override fun onClick(
                                                dialog: DialogInterface?,
                                                which: Int
                                            ) {
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
                                val cameraUpdate = CameraUpdateFactory.newCameraPosition(
                                    cameraPosition
                                )
                                googleMap.moveCamera(cameraUpdate)


                            }
                        })
                }
            }
        }
    }

}