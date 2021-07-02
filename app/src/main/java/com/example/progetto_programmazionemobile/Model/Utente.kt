package com.example.progetto_programmazionemobile.Model

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.content.ContextCompat.getSystemService
import java.io.Serializable
import java.util.*


class Utente(
    name: String,
    surname: String,
    nascita: Date?,
    user: String,
    email: String?,
    telefono: String?,
    password: String
) : Serializable {

    var username: String = user
    var nome: String = name
    var cognome: String = surname
    var nascita: Date? = nascita
    var email: String? = email
    var telefono: String? = telefono
    var password: String = password


    interface MyCallbackPosition {
        fun onCallback(position: Location?)
    }

    companion object {

        @SuppressLint("MissingPermission")
        fun getLocation(context: Context, myCallbackPosition: MyCallbackPosition) {
            var locationManager: LocationManager? =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (locationManager != null) {
                val gpsOn = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                val netOn = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                var netLocation: Location? = null
                var gpsLocation: Location? = null

                if (gpsOn || netOn) {
                    if (gpsOn) {
                        locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            10000,
                            0F,
                            object : LocationListener {
                                override fun onLocationChanged(location: Location?) {
                                    locationManager!!.removeUpdates(this)
                                    myCallbackPosition.onCallback(location)
                                }

                                override fun onProviderDisabled(provider: String?) {
                                }

                                override fun onProviderEnabled(provider: String?) {
                                }

                                override fun onStatusChanged(
                                    provider: String?,
                                    status: Int,
                                    extras: Bundle?
                                ) {

                                }
                            })

                    } else {

                        locationManager!!.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            10000,
                            0F,
                            object : LocationListener {
                                override fun onLocationChanged(location: Location?) {

                                    locationManager.removeUpdates(this)
                                    myCallbackPosition.onCallback(location)

                                }

                                override fun onProviderDisabled(provider: String?) {
                                }

                                override fun onProviderEnabled(provider: String?) {
                                }

                                override fun onStatusChanged(
                                    provider: String?,
                                    status: Int,
                                    extras: Bundle?
                                ) {

                                }
                            })
                    }
                }
            }
        }

    }
}

