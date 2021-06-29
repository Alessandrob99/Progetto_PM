package com.example.progetto_programmazionemobile.View

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.progetto_programmazionemobile.Model.Circolo
import com.example.progetto_programmazionemobile.R
import com.example.progetto_programmazionemobile.ViewModel.DB_Handler_Clubs
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoQueryBounds
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

@SuppressLint("MissingPermission")
class Selezione_2 : AppCompatActivity(), OnMapReadyCallback {


    val db_clubs = DB_Handler_Clubs()
    lateinit var found_clubs : ArrayList<Circolo>
    lateinit var bounds : GeoQueryBounds




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selezione_2)

        //leggo la mia posizione qui....metterla prima dell'on create causa il crash dell'applicazione
        val locMan : LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val myLocation : Location = locMan.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        val myLng = myLocation.longitude
        val myLat = myLocation.latitude

        //Leggo tutti i circoli
        db_clubs.getAllClubsInRange(object : DB_Handler_Clubs.MyCallbackClubs {
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
        },myLocation,25)

    }




    //DISEGNA LA MAPPA DI GOOGLE
    override fun onMapReady(googleMap: GoogleMap) {

        //leggo la mia posizione qui....metterla prima dell'on create causa il crash dell'applicazione
        val locMan : LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val myLocation : Location = locMan.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        val myLng = myLocation.longitude
        val myLat = myLocation.latitude
        //Possibile usarlo in un for loop ^^^
        /*var positions = mutableListOf<LatLng>()
        positions.add(LatLng(myLat,myLng))
        Toast.makeText(applicationContext,positions.size.toString(),Toast.LENGTH_SHORT).show()*/

        googleMap.addMarker(
            MarkerOptions().position(LatLng(myLat, myLng)).title("Tu sei qui!").icon(
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
            )
        )
        if(!found_clubs.isEmpty()){
            for(club in found_clubs){
                //Aggiungo marker del club sulla mappa
                googleMap.addMarker(
                    MarkerOptions().position(
                        LatLng(
                            club.posizione[0],
                            club.posizione[1]
                        )
                    ).title(club.nome)
                )
            }
        }else{
            val builder : AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setMessage("Nessun campo trovato")
            builder.setPositiveButton("OK",object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {

                }
            })
            val alertDialog = builder.create()
            alertDialog.show()
        }



        val cameraPosition = CameraPosition.Builder().target(LatLng(myLat, myLng)).zoom(10.0f).build()
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        googleMap.moveCamera(cameraUpdate)

    }
}