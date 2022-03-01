package com.example.progetto_programmazionemobile.View

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.location.LocationManagerCompat
import com.example.progetto_programmazionemobile.Model.Court
import com.example.progetto_programmazionemobile.R
import com.example.progetto_programmazionemobile.ViewModel.DB_Handler_Courts
import java.util.*

class LoaderLocation : AppCompatActivity()
{
    lateinit var locationManager: LocationManager
    private var hasGps = false
    private var hasNetwork = false

    interface MyCallbackPosition {
        fun onCallback(latitude: Double, longitude: Double)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loader_location)


        val giorno = intent.getStringExtra("giorno")
        val sport = intent.getStringExtra("sport")

        var locMan: LocationManager? = getSystemService(Context.LOCATION_SERVICE) as LocationManager



        /** Controllo se ha attiva la geocalizzazione  **/
        if (locMan != null)
        {
            if (LocationManagerCompat.isLocationEnabled(locMan))
            {
                /** Se la geocalizzazione è attiva, prende la longitudine e latitudine del client  **/
                getLocation(object : MyCallbackPosition {
                    /** Trovo prima la posizione (attraverso la callBack) e poi passo all'intent successivo  **/
                    override fun onCallback(latitude: Double, longitude: Double) {
                        /** Facciamo partire la funzione per la ricerca di campi per SPORT ( se lo sport è != null ) **/
                            DB_Handler_Courts.getCourtsBySport(sport.toString(),
                                object : DB_Handler_Courts.MyCallbackCourts {
                                    override fun onCallback(returnedCourts: ArrayList<Court>?) {
                                        val intent =
                                            Intent(
                                                this@LoaderLocation,
                                                ClubSelection::class.java
                                            )
                                        intent.putExtra("giorno",giorno)
                                        intent.putExtra("latitude", latitude)
                                        intent.putExtra("longitude", longitude)
                                        //intent.putExtra("giorno",giorno)
                                        intent.putExtra("campiPerSport", returnedCourts)
                                        startActivity(intent)
                                        finish()
                                    }
                                })
                    }
                })
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation(MyCallback: MyCallbackPosition) {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (hasGps || hasNetwork) {

            if (hasNetwork) {

                //-------------
                Log.d("CodeAndroidLocation", "hasGps")
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    5000,
                    100F,
                    object :
                        LocationListener {
                        override fun onLocationChanged(location: Location?) {
                            if (location != null) {
                                locationManager.removeUpdates(this)
                                MyCallback.onCallback(
                                    location!!.latitude,
                                    location!!.longitude
                                )
                            }
                        }
                        override fun onStatusChanged(
                            provider: String?,
                            status: Int,
                            extras: Bundle?
                        ) {
                        }
                        override fun onProviderEnabled(provider: String?) {
                        }
                        override fun onProviderDisabled(provider: String?) {
                        }
                    })
            }else{
                Log.d("CodeAndroidLocation", "hasGps")
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5000,
                    100F,
                    object : LocationListener {
                        override fun onLocationChanged(location: Location?) {
                            if (location != null) {
                                locationManager.removeUpdates(this)
                                MyCallback.onCallback(
                                    location!!.latitude,
                                    location!!.longitude
                                )


                            }
                        }

                        override fun onStatusChanged(
                            provider: String?,
                            status: Int,
                            extras: Bundle?
                        ) {

                        }

                        override fun onProviderEnabled(provider: String?) {

                        }

                        override fun onProviderDisabled(provider: String?) {

                        }

                    })
            }


        }

    }

    override fun onBackPressed() {
        //controllo sul tempo o su un eventuale crash
    }
}