package com.example.progetto_programmazionemobile.View

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.progetto_programmazionemobile.Model.Prenotazione
import com.example.progetto_programmazionemobile.R
import com.example.progetto_programmazionemobile.ViewModel.DB_Handler_Reservation
import kotlinx.android.synthetic.main.fragment_home_profile_fragment.*
import java.util.*
import kotlin.collections.ArrayList

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentOrari.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentOrari : Fragment(), View.OnClickListener {

    var flagClick = false
    lateinit var oraInizioStr: String
    lateinit var oraFineStr: String
    lateinit var textView: TextView
    lateinit var btnOrari: MutableMap<String, Button>
    var prenotazioni = ArrayList<Prenotazione>()


    //Da leggere
    val giorno = "28-07-2021"
    val campo = 1.toLong()
    val circolo = 1.toLong()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v: View = inflater.inflate(R.layout.fragment_orari, container, false)

        textView = v.findViewById<TextView>(R.id.textView8)
        var flagClick = false

        btnOrari = mutableMapOf("uno" to v.findViewById(R.id.h6m30))

        btnOrari.put("6:30", v.findViewById(R.id.h6m30))
        btnOrari.put("7:00", v.findViewById(R.id.h7m00))
        btnOrari.put("7:30", v.findViewById(R.id.h7m30))
        btnOrari.put("8:00", v.findViewById(R.id.h8m00))
        btnOrari.put("8:30", v.findViewById(R.id.h8m30))
        btnOrari.put("9:00", v.findViewById(R.id.h9m00))
        btnOrari.put("9:30", v.findViewById(R.id.h9m30))
        btnOrari.put("10:00", v.findViewById(R.id.h10m00))
        btnOrari.put("10:30", v.findViewById(R.id.h10m30))
        btnOrari.put("11:00", v.findViewById(R.id.h11m00))
        btnOrari.put("11:30", v.findViewById(R.id.h11m30))
        btnOrari.put("12:00", v.findViewById(R.id.h12m00))
        btnOrari.put("12:30", v.findViewById(R.id.h12m30))
        btnOrari.put("13:00", v.findViewById(R.id.h13m00))
        btnOrari.put("13:30", v.findViewById(R.id.h13m30))
        btnOrari.put("14:00", v.findViewById(R.id.h14m00))
        btnOrari.put("14:30", v.findViewById(R.id.h14m30))
        btnOrari.put("15:00", v.findViewById(R.id.h15m00))
        btnOrari.put("15:30", v.findViewById(R.id.h15m30))
        btnOrari.put("16:00", v.findViewById(R.id.h16m00))
        btnOrari.put("16:30", v.findViewById(R.id.h16m30))
        btnOrari.put("17:00", v.findViewById(R.id.h17m00))
        btnOrari.put("17:30", v.findViewById(R.id.h17m30))
        btnOrari.put("18:00", v.findViewById(R.id.h18m00))
        btnOrari.put("18:30", v.findViewById(R.id.h18m30))
        btnOrari.put("19:00", v.findViewById(R.id.h19m00))
        btnOrari.put("19:30", v.findViewById(R.id.h19m30))
        btnOrari.put("20:00", v.findViewById(R.id.h20m00))
        btnOrari.put("20:30", v.findViewById(R.id.h20m30))
        btnOrari.put("21:00", v.findViewById(R.id.h21m00))
        btnOrari.put("21:30", v.findViewById(R.id.h21m30))
        btnOrari.put("22:00", v.findViewById(R.id.h22m00))
        btnOrari.put("22:30", v.findViewById(R.id.h22m30))
        btnOrari.put("23:00", v.findViewById(R.id.h23m00))
        btnOrari.put("23:30", v.findViewById(R.id.h23m30))
        btnOrari.put("24:00", v.findViewById(R.id.h24m00))


        btnOrari.get("6:30")!!.isEnabled = false
        btnOrari.get("7:00")!!.isEnabled = false
        btnOrari.get("7:30")!!.isEnabled = false
        btnOrari.get("8:00")!!.isEnabled = false
        btnOrari.get("8:30")!!.isEnabled = false
        btnOrari.get("9:00")!!.isEnabled = false
        btnOrari.get("9:30")!!.isEnabled = false
        btnOrari.get("10:00")!!.isEnabled = false
        btnOrari.get("10:30")!!.isEnabled = false
        btnOrari.get("11:00")!!.isEnabled = false
        btnOrari.get("11:30")!!.isEnabled = false
        btnOrari.get("12:00")!!.isEnabled = false
        btnOrari.get("12:30")!!.isEnabled = false
        btnOrari.get("13:00")!!.isEnabled = false
        btnOrari.get("13:30")!!.isEnabled = false
        btnOrari.get("14:00")!!.isEnabled = false
        btnOrari.get("14:30")!!.isEnabled = false
        btnOrari.get("15:00")!!.isEnabled = false
        btnOrari.get("15:30")!!.isEnabled = false
        btnOrari.get("16:00")!!.isEnabled = false
        btnOrari.get("16:30")!!.isEnabled = false
        btnOrari.get("17:00")!!.isEnabled = false
        btnOrari.get("17:30")!!.isEnabled = false
        btnOrari.get("18:00")!!.isEnabled = false
        btnOrari.get("18:30")!!.isEnabled = false
        btnOrari.get("19:00")!!.isEnabled = false
        btnOrari.get("19:30")!!.isEnabled = false
        btnOrari.get("20:00")!!.isEnabled = false
        btnOrari.get("20:30")!!.isEnabled = false
        btnOrari.get("21:00")!!.isEnabled = false
        btnOrari.get("21:30")!!.isEnabled = false
        btnOrari.get("22:00")!!.isEnabled = false
        btnOrari.get("22:30")!!.isEnabled = false
        btnOrari.get("23:00")!!.isEnabled = false
        btnOrari.get("23:30")!!.isEnabled = false
        btnOrari.get("24:00")!!.isEnabled = false


        val prenotazioni = DB_Handler_Reservation.getListOfReservations(
            giorno,
            campo,
            circolo,
            object : DB_Handler_Reservation.MyCallbackReservations {
                override fun onCallback(reservations: ArrayList<Prenotazione>) {
                    btnOrari.get("6:30")!!.isEnabled = true
                    btnOrari.get("7:00")!!.isEnabled = true
                    btnOrari.get("7:30")!!.isEnabled = true
                    btnOrari.get("8:00")!!.isEnabled = true
                    btnOrari.get("8:30")!!.isEnabled = true
                    btnOrari.get("9:00")!!.isEnabled = true
                    btnOrari.get("9:30")!!.isEnabled = true
                    btnOrari.get("10:00")!!.isEnabled = true
                    btnOrari.get("10:30")!!.isEnabled = true
                    btnOrari.get("11:00")!!.isEnabled = true
                    btnOrari.get("11:30")!!.isEnabled = true
                    btnOrari.get("12:00")!!.isEnabled = true
                    btnOrari.get("12:30")!!.isEnabled = true
                    btnOrari.get("13:00")!!.isEnabled = true
                    btnOrari.get("13:30")!!.isEnabled = true
                    btnOrari.get("14:00")!!.isEnabled = true
                    btnOrari.get("14:30")!!.isEnabled = true
                    btnOrari.get("15:00")!!.isEnabled = true
                    btnOrari.get("15:30")!!.isEnabled = true
                    btnOrari.get("16:00")!!.isEnabled = true
                    btnOrari.get("16:30")!!.isEnabled = true
                    btnOrari.get("17:00")!!.isEnabled = true
                    btnOrari.get("17:30")!!.isEnabled = true
                    btnOrari.get("18:00")!!.isEnabled = true
                    btnOrari.get("18:30")!!.isEnabled = true
                    btnOrari.get("19:00")!!.isEnabled = true
                    btnOrari.get("19:30")!!.isEnabled = true
                    btnOrari.get("20:00")!!.isEnabled = true
                    btnOrari.get("20:30")!!.isEnabled = true
                    btnOrari.get("21:00")!!.isEnabled = true
                    btnOrari.get("21:30")!!.isEnabled = true
                    btnOrari.get("22:00")!!.isEnabled = true
                    btnOrari.get("22:30")!!.isEnabled = true
                    btnOrari.get("23:00")!!.isEnabled = true
                    btnOrari.get("23:30")!!.isEnabled = true
                    btnOrari.get("24:00")!!.isEnabled = true
                    var minFine: String
                    var oraFine: String
                    var min: Int
                    var ora: Int
                    val cal: Calendar = Calendar.getInstance()
                    var btn: Button
                    for (prenotazione in reservations) {
                        prenotazioni.add(prenotazione)
                        cal.time = prenotazione.oraInizio
                        min = cal.get(Calendar.MINUTE)
                        ora = cal.get(Calendar.HOUR_OF_DAY)
                        cal.time = prenotazione.oraFine
                        minFine = cal.get(Calendar.MINUTE).toString()
                        oraFine = cal.get(Calendar.HOUR_OF_DAY).toString()
                        while (ora.toString() + ":" + min.toString() != oraFine + ":" + minFine) {


                            if (min == 0) {
                                btn = btnOrari.get(ora.toString() + ":00")!!

                            } else {
                                btn = btnOrari.get(ora.toString() + ":" + min.toString())!!
                            }
                            btn.setBackgroundColor(Color.RED)
                            btn.isEnabled = false

                            //Aggiungo mezz'ora
                            if (min == 30) {
                                min = 0
                                ora += 1
                            } else {
                                min = 30
                            }
                        }

                    }

                }
            })
        btnOrari.get("6:30")!!.setOnClickListener(this)
        btnOrari.get("7:00")!!.setOnClickListener(this)
        btnOrari.get("7:30")!!.setOnClickListener(this)
        btnOrari.get("8:00")!!.setOnClickListener(this)
        btnOrari.get("8:30")!!.setOnClickListener(this)
        btnOrari.get("9:00")!!.setOnClickListener(this)
        btnOrari.get("9:30")!!.setOnClickListener(this)
        btnOrari.get("10:00")!!.setOnClickListener(this)
        btnOrari.get("10:30")!!.setOnClickListener(this)
        btnOrari.get("11:00")!!.setOnClickListener(this)
        btnOrari.get("11:30")!!.setOnClickListener(this)
        btnOrari.get("12:00")!!.setOnClickListener(this)
        btnOrari.get("12:30")!!.setOnClickListener(this)
        btnOrari.get("13:00")!!.setOnClickListener(this)
        btnOrari.get("13:30")!!.setOnClickListener(this)
        btnOrari.get("14:00")!!.setOnClickListener(this)
        btnOrari.get("14:30")!!.setOnClickListener(this)
        btnOrari.get("15:00")!!.setOnClickListener(this)
        btnOrari.get("15:30")!!.setOnClickListener(this)
        btnOrari.get("16:00")!!.setOnClickListener(this)
        btnOrari.get("16:30")!!.setOnClickListener(this)
        btnOrari.get("17:00")!!.setOnClickListener(this)
        btnOrari.get("17:30")!!.setOnClickListener(this)
        btnOrari.get("18:00")!!.setOnClickListener(this)
        btnOrari.get("18:30")!!.setOnClickListener(this)
        btnOrari.get("19:00")!!.setOnClickListener(this)
        btnOrari.get("19:30")!!.setOnClickListener(this)
        btnOrari.get("20:00")!!.setOnClickListener(this)
        btnOrari.get("20:30")!!.setOnClickListener(this)
        btnOrari.get("21:00")!!.setOnClickListener(this)
        btnOrari.get("21:30")!!.setOnClickListener(this)
        btnOrari.get("22:00")!!.setOnClickListener(this)
        btnOrari.get("22:30")!!.setOnClickListener(this)
        btnOrari.get("23:00")!!.setOnClickListener(this)
        btnOrari.get("23:30")!!.setOnClickListener(this)
        btnOrari.get("24:00")!!.setOnClickListener(this)


        //Settiamo il comportamento sugli onClick


        return v

    }

    @SuppressLint("ResourceAsColor")
    fun firstClick(btntId: String) {
        oraInizioStr = btntId

        flagClick = true
        textView.text = "Selezionare orario di fine"
        btnOrari.get(btntId)!!.isEnabled = true
        btnOrari.get(btntId)!!.setBackgroundColor(Color.CYAN)
        btnOrari.get("24:00")!!.isEnabled = true
        var ora = "6:30".split(":") as MutableList<String>
        while (oraInizioStr != ora[0] + ":" + ora[1]) {
            btnOrari.get(ora[0] + ":" + ora[1])!!.isEnabled = false
            btnOrari.get(ora[0] + ":" + ora[1])!!.setBackgroundColor(Color.GRAY)
            //aggiungo mezzo'ora
            if (ora[1] == "30") {
                ora[1] = "00"
                var app = ora[0].toInt()
                app+=1
                ora[0] = app.toString()
            } else {
                ora[1] = "30"
            }
        }
        //Libero i tasti corrispondendti agli inizi delle prenotazione per permettere di usarli come fine della nuova prenotazione
        var oraIn : String
        for(prenotazione in prenotazioni){
            val cal: Calendar = Calendar.getInstance()
            cal.time = prenotazione.oraInizio
            var minPren =""
            if(cal.get(Calendar.MINUTE)==0){
                minPren ="00"
            }else{
                minPren ="30"
            }

            var splitOraInizio = oraInizioStr.split(":")

            if(cal.get(Calendar.HOUR_OF_DAY)>splitOraInizio[0].toInt()){
                btnOrari.get(cal.get(Calendar.HOUR_OF_DAY).toString()+":"+minPren)!!.isEnabled = true
                btnOrari.get(cal.get(Calendar.HOUR_OF_DAY).toString()+":"+minPren)!!.setBackgroundResource(R.drawable.common_google_signin_btn_icon_dark_normal)
            }else{
                if(cal.get(Calendar.HOUR_OF_DAY)==splitOraInizio[0].toInt() && minPren=="30" && splitOraInizio[1].toString()=="00"){
                    btnOrari.get(cal.get(Calendar.HOUR_OF_DAY).toString()+":"+minPren)!!.isEnabled = true
                    btnOrari.get(cal.get(Calendar.HOUR_OF_DAY).toString()+":"+minPren)!!.setBackgroundResource(R.drawable.common_google_signin_btn_icon_dark_normal)
                }
            }

        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun secondClick(btntId: String) {
        oraFineStr = btntId
        DB_Handler_Reservation.checkAvailability(
            giorno,
            oraInizioStr,
            oraFineStr,
            campo,
            circolo,
            object : DB_Handler_Reservation.MyCallbackAvailable {
                override fun onCallback(result: Boolean) {
                    if (result) {
                        //Sono presenti sovrapposizioni
                        Toast.makeText(context, "Errore", Toast.LENGTH_SHORT).show()
                        //resetta()
                    } else {
                        //Nessuna sovrapposizione
                        Toast.makeText(context, "Prenotazione effettuata", Toast.LENGTH_SHORT)
                            .show()

                    }
                }
            })

    }

    fun resetta() {
        flagClick = false
        oraFineStr = ""
        oraInizioStr = ""
        textView.text = "Selezionare orario di inizio"


        btnOrari.get("6:30")!!.setBackgroundColor(Color.WHITE)
        btnOrari.get("7:00")!!.setBackgroundColor(Color.WHITE)
        btnOrari.get("7:30")!!.setBackgroundColor(Color.WHITE)
        btnOrari.get("8:00")!!.setBackgroundColor(Color.WHITE)
        btnOrari.get("8:30")!!.setBackgroundColor(Color.WHITE)
        btnOrari.get("9:00")!!.setBackgroundColor(Color.WHITE)
        btnOrari.get("9:30")!!.setBackgroundColor(Color.WHITE)
        btnOrari.get("10:00")!!.setBackgroundColor(Color.WHITE)
        btnOrari.get("10:30")!!.setBackgroundColor(Color.WHITE)
        btnOrari.get("11:00")!!.setBackgroundColor(Color.WHITE)
        btnOrari.get("11:30")!!.setBackgroundColor(Color.WHITE)
        btnOrari.get("12:00")!!.setBackgroundColor(Color.WHITE)
        btnOrari.get("12:30")!!.setBackgroundColor(Color.WHITE)
        btnOrari.get("13:00")!!.setBackgroundColor(Color.WHITE)
        btnOrari.get("13:30")!!.setBackgroundColor(Color.WHITE)
        btnOrari.get("14:00")!!.setBackgroundColor(Color.WHITE)
        btnOrari.get("14:30")!!.setBackgroundColor(Color.WHITE)
        btnOrari.get("15:00")!!.setBackgroundColor(Color.WHITE)
        btnOrari.get("15:30")!!.setBackgroundColor(Color.WHITE)
        btnOrari.get("16:00")!!.setBackgroundColor(Color.WHITE)
        btnOrari.get("16:30")!!.setBackgroundColor(Color.WHITE)
        btnOrari.get("17:00")!!.setBackgroundColor(Color.WHITE)
        btnOrari.get("17:30")!!.setBackgroundColor(Color.WHITE)
        btnOrari.get("18:00")!!.setBackgroundColor(Color.WHITE)
        btnOrari.get("18:30")!!.setBackgroundColor(Color.WHITE)
        btnOrari.get("19:00")!!.setBackgroundColor(Color.WHITE)
        btnOrari.get("19:30")!!.setBackgroundColor(Color.WHITE)
        btnOrari.get("20:00")!!.setBackgroundColor(Color.WHITE)
        btnOrari.get("20:30")!!.setBackgroundColor(Color.WHITE)
        btnOrari.get("21:00")!!.setBackgroundColor(Color.WHITE)
        btnOrari.get("21:30")!!.setBackgroundColor(Color.WHITE)
        btnOrari.get("22:00")!!.setBackgroundColor(Color.WHITE)
        btnOrari.get("22:30")!!.setBackgroundColor(Color.WHITE)
        btnOrari.get("23:00")!!.setBackgroundColor(Color.WHITE)
        btnOrari.get("23:30")!!.setBackgroundColor(Color.WHITE)
        btnOrari.get("24:00")!!.setBackgroundColor(Color.WHITE)


    }

    override fun onClick(v: View?) {
        @RequiresApi(Build.VERSION_CODES.O)
        when (v!!.id) {
            R.id.h6m30 -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("6:30")
                } else {
                    //Bottone già cliccato
                    secondClick("6:30")
                }
            }
            R.id.h7m00 -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("7:00")
                } else {
                    //Bottone già cliccato
                    secondClick("7:00")
                }
            }

            R.id.h7m30 -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("7:30")
                } else {
                    //Bottone già cliccato
                    secondClick("7:30")
                }
            }
            R.id.h8m00 -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("8:00")
                } else {
                    //Bottone già cliccato
                    secondClick("8:00")
                }
            }
            R.id.h8m30 -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("8:30")
                } else {
                    //Bottone già cliccato
                    secondClick("8:30")
                }
            }
            R.id.h9m00 -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("9:00")
                } else {
                    //Bottone già cliccato
                    secondClick("9:00")
                }
            }
            R.id.h9m30 -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("9:30")
                } else {
                    //Bottone già cliccato
                    secondClick("9:30")
                }
            }
            R.id.h10m00 -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("10:00")
                } else {
                    //Bottone già cliccato
                    secondClick("10:00")
                }
            }
            R.id.h10m30 -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("10:30")
                } else {
                    //Bottone già cliccato
                    secondClick("10:30")
                }
            }
            R.id.h11m00 -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("11:00")
                } else {
                    //Bottone già cliccato
                    secondClick("11:00")
                }
            }
            R.id.h11m30 -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("11:30")
                } else {
                    //Bottone già cliccato
                    secondClick("11:30")
                }
            }
            R.id.h12m00 -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("12:00")
                } else {
                    //Bottone già cliccato
                    secondClick("12:00")
                }
            }
            R.id.h12m30 -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("12:30")
                } else {
                    //Bottone già cliccato
                    secondClick("12:30")
                }
            }
            R.id.h13m00 -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("13:00")
                } else {
                    //Bottone già cliccato
                    secondClick("13:00")
                }
            }
            R.id.h13m30 -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("13:30")
                } else {
                    //Bottone già cliccato
                    secondClick("13:30")
                }
            }
            R.id.h14m00 -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("14:00")
                } else {
                    //Bottone già cliccato
                    secondClick("14:00")
                }
            }
            R.id.h14m30 -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("14:30")
                } else {
                    //Bottone già cliccato
                    secondClick("14:30")
                }
            }
            R.id.h15m00 -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("15:00")
                } else {
                    //Bottone già cliccato
                    secondClick("15:00")
                }
            }
            R.id.h15m30 -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("15:30")
                } else {
                    //Bottone già cliccato
                    secondClick("15:30")
                }
            }
            R.id.h16m00 -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("16:00")
                } else {
                    //Bottone già cliccato
                    secondClick("16:00")
                }
            }
            R.id.h16m30 -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("16:30")
                } else {
                    //Bottone già cliccato
                    secondClick("16:30")
                }
            }
            R.id.h17m00 -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("17:00")
                } else {
                    //Bottone già cliccato
                    secondClick("17:00")
                }
            }
            R.id.h17m30 -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("17:30")
                } else {
                    //Bottone già cliccato
                    secondClick("17:30")
                }
            }
            R.id.h18m00 -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("18:00")
                } else {
                    //Bottone già cliccato
                    secondClick("18:00")
                }
            }
            R.id.h18m30 -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("18:30")
                } else {
                    //Bottone già cliccato
                    secondClick("18:30")
                }
            }
            R.id.h19m00 -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("19:00")
                } else {
                    //Bottone già cliccato
                    secondClick("19:00")
                }
            }
            R.id.h19m30 -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("19:30")
                } else {
                    //Bottone già cliccato
                    secondClick("19:30")
                }
            }
            R.id.h20m00 -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("20:00")
                } else {
                    //Bottone già cliccato
                    secondClick("20:00")
                }
            }
            R.id.h20m30 -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("20:30")
                } else {
                    //Bottone già cliccato
                    secondClick("20:30")
                }
            }
            R.id.h21m00 -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("21:00")
                } else {
                    //Bottone già cliccato
                    secondClick("21:00")
                }
            }
            R.id.h21m30 -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("21:30")
                } else {
                    //Bottone già cliccato
                    secondClick("21:30")
                }
            }
            R.id.h22m00 -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("22:00")
                } else {
                    //Bottone già cliccato
                    secondClick("22:00")
                }
            }
            R.id.h22m30 -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("22:30")
                } else {
                    //Bottone già cliccato
                    secondClick("22:30")
                }
            }
            R.id.h23m00 -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("23:00")
                } else {
                    //Bottone già cliccato
                    secondClick("23:00")
                }
            }
            R.id.h23m30 -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("23:30")
                } else {
                    //Bottone già cliccato
                    secondClick("23:30")
                }
            }
            R.id.h24m00 ->
                secondClick("24:00")
        }
    }

}