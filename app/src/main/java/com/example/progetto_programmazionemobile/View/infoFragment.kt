package com.example.progetto_programmazionemobile.View

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Telephony
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.example.progetto_programmazionemobile.R
import com.example.progetto_programmazionemobile.ViewModel.Auth_Handler

class infoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_info, container, false)

        val welcomeText = v.findViewById<TextView>(R.id.welcome)
        welcomeText.setText("Benvenuto "+ (Auth_Handler.CURRENT_USER!!.nome!!.capitalize()))

        val btnUnisciti : Button = v.findViewById(R.id.btnUniscitiPrenotazione)
        val btnPrenota : Button = v.findViewById(R.id.btnPrenotazione)
        val btnInfo : Button = v.findViewById(R.id.InfoApplicazione)
        val btnLeMiePrenotazioni : Button = v.findViewById(R.id.VisualizzaPrenotazione)


        //Va al fragment dell'info App
        btnInfo.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?){
                var fr = getFragmentManager()?.beginTransaction()
                fr?.replace(R.id.fragment_container, InfoApp(),"APP")
                fr?.commit()
            }
        })

        //Nuova Prenotazione
        btnPrenota.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                if (ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //PERMESSO GARANTITO
                    val goToSelection = Intent(v?.context,Selezione_1::class.java)
                    startActivity(goToSelection)
                } else {
                    if(ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED){
                        //RICHIESTA PERMESSO DI ACCESO ALLA POSIZIONE
                        //Informiamo l'utente che l'app ha bisogno della posizione
                        //POPUP Registrazione compleatta + redirect alla mainactivity
                        val builder : AlertDialog.Builder = AlertDialog.Builder(context!!)
                        builder.setTitle("NOTA BENE")
                        builder.setMessage("Non è possibile accedere a questa funzionalità senza il permesso di geolocalizzare il dispositivo." +System.lineSeparator()+
                                System.lineSeparator()+"E' possibile fornire l'autorizzazione dalle impostazioni.")
                        builder.setPositiveButton("OK",object : DialogInterface.OnClickListener{
                            override fun onClick(dialog: DialogInterface?, which: Int) {

                            }
                        })
                        val alertDialog = builder.create()
                        alertDialog.show()
                    }

                }

            }
        })


        //Partecipa a prenotazione
        btnUnisciti.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var fr = getFragmentManager()?.beginTransaction()
                fr?.replace(R.id.fragment_container, Partecipa(),"APP")
                fr?.commit()
            }
        })



        //Visualizza le mie prenotazioni
        btnLeMiePrenotazioni.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var fr = getFragmentManager()?.beginTransaction()
                fr?.replace(R.id.fragment_container, LeMiePrenotazioni(),"APP")
                fr?.commit()
            }
        })

        return v

    }

}