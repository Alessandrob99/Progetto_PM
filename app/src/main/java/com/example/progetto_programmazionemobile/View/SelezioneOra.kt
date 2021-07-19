package com.example.progetto_programmazionemobile.View

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.*
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.progetto_programmazionemobile.Model.Prenotazione
import com.example.progetto_programmazionemobile.R
import com.example.progetto_programmazionemobile.ViewModel.Auth_Handler
import com.example.progetto_programmazionemobile.ViewModel.DB_Handler_Reservation
import kotlinx.android.synthetic.main.activity_selezione_ora.*
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class SelezioneOra : AppCompatActivity(), View.OnClickListener {
    var flagClick = false
    lateinit var oraInizioStr: String
    lateinit var oraFineStr: String
    lateinit var btnOrari: MutableMap<String, Button>
    var prenotazioni = ArrayList<Prenotazione>()

    //Da leggere





    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val giorno = intent.getStringExtra("giorno")
        val campo = intent.getStringExtra("n_campo").toLong()
        val circolo = intent.getLongExtra("club", 0L)
        setContentView(R.layout.activity_selezione_ora)


        val broadcastReceiver1: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(arg0: Context?, intent: Intent) {
                val action = intent.action
                if (action == "logout") {
                    finish()
                    // DO WHATEVER YOU WANT.
                }
            }
        }
        registerReceiver(broadcastReceiver1, IntentFilter("logout"))


        // Inflate the layout for this fragment

        flagClick = false

        Annulla.isEnabled = false
        Annulla.setBackgroundColor(Color.GRAY)
        Annulla.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                resetta()
            }
        })
        ConfermaOrario.isEnabled = false
        ConfermaOrario.setBackgroundColor(Color.GRAY)
        ConfermaOrario.setOnClickListener(object : View.OnClickListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onClick(v: View?) {
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
                                val builder: AlertDialog.Builder =
                                    AlertDialog.Builder(this@SelezioneOra)
                                builder.setTitle("Errore")
                                builder.setMessage(
                                    "L'orario selezionato va in collisione con altre prenotazioni già presenti." +
                                            System.lineSeparator() + "Inserire un intervallo temporale valido"
                                )

                                builder.setNegativeButton(
                                    "OK",
                                    object : DialogInterface.OnClickListener {
                                        override fun onClick(dialog: DialogInterface?, which: Int) {

                                        }
                                    })
                                val alertDialog = builder.create()
                                alertDialog.show()
                                resetta()
                            } else {
                                val builder: AlertDialog.Builder =
                                    AlertDialog.Builder(this@SelezioneOra)
                                builder.setTitle("Sei sicuro?")
                                builder.setMessage(
                                    "Confermare la prenotazione? " + System.getProperty(
                                        "line.separator"
                                    ) + "Dalle " + oraInizioStr + System.getProperty("line.separator") + "Alle " + oraFineStr + System.getProperty(
                                        "line.separator"
                                    ) + "Del giorno " + giorno
                                )

                                builder.setNegativeButton("NO",
                                    object : DialogInterface.OnClickListener {
                                        override fun onClick(dialog: DialogInterface?, which: Int) {
                                            resetta()
                                        }
                                    })
                                builder.setPositiveButton("SI",
                                    object : DialogInterface.OnClickListener {
                                        override fun onClick(dialog: DialogInterface?, which: Int) {

                                            val prog: ProgressDialog = ProgressDialog.show(
                                                this@SelezioneOra,
                                                "",
                                                "Registrando la nuova prenotazione..."
                                            )

                                            val oraInizioTime: LocalDateTime
                                            val oraFineTime: LocalDateTime

                                            //Formatter diversi
                                            var split = oraInizioStr.split(":")
                                            var formatter = DateTimeFormatter.ofPattern(
                                                "dd-MM-yyyy HH:mm",
                                                Locale.ITALY
                                            )

                                            if (split[0].length == 1) {
                                                formatter = DateTimeFormatter.ofPattern(
                                                    "dd-MM-yyyy H:mm",
                                                    Locale.ITALY
                                                )
                                            } else {
                                                formatter = DateTimeFormatter.ofPattern(
                                                    "dd-MM-yyyy HH:mm",
                                                    Locale.ITALY
                                                )
                                            }
                                            oraInizioTime = LocalDateTime.parse(
                                                giorno + " " + oraInizioStr,
                                                formatter
                                            )
                                            split = oraFineStr.split(":")
                                            if (split[0].length == 1) {
                                                formatter = DateTimeFormatter.ofPattern(
                                                    "dd-MM-yyyy H:mm",
                                                    Locale.ITALY
                                                )
                                            } else {
                                                formatter = DateTimeFormatter.ofPattern(
                                                    "dd-MM-yyyy HH:mm",
                                                    Locale.ITALY
                                                )
                                            }
                                            oraFineTime = LocalDateTime.parse(
                                                giorno + " " + oraFineStr,
                                                formatter
                                            )

                                            //Genero codice prenotazione
                                            val cod_pren = DB_Handler_Reservation.cipher(
                                                circolo.toString() + "&" + campo.toString() + "&" + giorno + "&" + oraInizioStr,
                                                15
                                            )

                                            DB_Handler_Reservation.newReservation(
                                                Auth_Handler.CURRENT_USER!!.email,
                                                circolo.toString(),
                                                campo.toString(),
                                                giorno,
                                                oraInizioTime.atOffset(ZoneOffset.ofHours(2))
                                                    .toInstant().toEpochMilli(),
                                                oraFineTime.atOffset(ZoneOffset.ofHours(2))
                                                    .toInstant().toEpochMilli(),
                                                cod_pren,
                                                object : DB_Handler_Reservation.MyCallBackNewRes {
                                                    override fun onCallback(result: Boolean) {
                                                        if (result) {
                                                            //Mostra Avviso
                                                            prog.dismiss()
                                                            val builder: AlertDialog.Builder =
                                                                AlertDialog.Builder(this@SelezioneOra)
                                                            builder.setTitle("Operazione conclusa")
                                                            builder.setMessage("Prenotazione registrata con successo")
                                                            builder.setPositiveButton("Ok",
                                                                object :
                                                                    DialogInterface.OnClickListener {
                                                                    override fun onClick(
                                                                        dialog: DialogInterface?,
                                                                        which: Int
                                                                    ) {
                                                                        val broadcastIntent = Intent()
                                                                        broadcastIntent.action = "finish_activity"
                                                                        sendBroadcast(broadcastIntent)
                                                                        /*val intent = Intent(
                                                                            this@SelezioneOra,
                                                                            HomePage_Activity::class.java
                                                                        )*/
                                                                        startActivity(intent)
                                                                        finish()
                                                                    }
                                                                })
                                                            builder.setOnDismissListener {
                                                                val intent = Intent(
                                                                    this@SelezioneOra,
                                                                    HomePage_Activity::class.java
                                                                )
                                                                startActivity(intent)
                                                                finish()
                                                            }
                                                            val alertDialog = builder.create()
                                                            alertDialog.show()
                                                        } else {
                                                            //Mostra Avviso
                                                            prog.dismiss()
                                                            val builder: AlertDialog.Builder =
                                                                AlertDialog.Builder(this@SelezioneOra)
                                                            builder.setTitle("Errore")
                                                            builder.setMessage("Qualcosa è andato storto...")
                                                            builder.setPositiveButton("Riprova",
                                                                object :
                                                                    DialogInterface.OnClickListener {
                                                                    override fun onClick(
                                                                        dialog: DialogInterface?,
                                                                        which: Int
                                                                    ) {
                                                                        Auth_Handler.setLOGGET_OUT(
                                                                            context = applicationContext
                                                                        )
                                                                        val intent = Intent(
                                                                            this@SelezioneOra,
                                                                            HomePage_Activity::class.java
                                                                        )
                                                                        startActivity(intent)

                                                                        //Terminare tutte le activity dopo la Home
                                                                        finish()
                                                                    }
                                                                })
                                                            val alertDialog = builder.create()
                                                            alertDialog.show()

                                                        }
                                                    }
                                                }
                                            )
                                        }
                                    })
                                val alertDialog = builder.create()
                                alertDialog.show()
                            }

                        }

                    })
            }
        })

        btnOrari = mutableMapOf("uno" to h6m30)

        btnOrari.put("6:30", h6m30)
        btnOrari.put("7:00", h7m00)
        btnOrari.put("7:30", h7m30)
        btnOrari.put("8:00", h8m00)
        btnOrari.put("8:30", h8m30)
        btnOrari.put("9:00", h9m00)
        btnOrari.put("9:30", h9m30)
        btnOrari.put("10:00", h10m00)
        btnOrari.put("10:30", h10m30)
        btnOrari.put("11:00", h11m00)
        btnOrari.put("11:30", h11m30)
        btnOrari.put("12:00", h12m00)
        btnOrari.put("12:30", h12m30)
        btnOrari.put("13:00", h13m00)
        btnOrari.put("13:30", h13m30)
        btnOrari.put("14:00", h14m00)
        btnOrari.put("14:30", h14m30)
        btnOrari.put("15:00", h15m00)
        btnOrari.put("15:30", h15m30)
        btnOrari.put("16:00", h16m00)
        btnOrari.put("16:30", h16m30)
        btnOrari.put("17:00", h17m00)
        btnOrari.put("17:30", h17m30)
        btnOrari.put("18:00", h18m00)
        btnOrari.put("18:30", h18m30)
        btnOrari.put("19:00", h19m00)
        btnOrari.put("19:30", h19m30)
        btnOrari.put("20:00", h20m00)
        btnOrari.put("20:30", h20m30)
        btnOrari.put("21:00", h21m00)
        btnOrari.put("21:30", h21m30)
        btnOrari.put("22:00", h22m00)
        btnOrari.put("22:30", h22m30)
        btnOrari.put("23:00", h23m00)
        btnOrari.put("23:30", h23m30)
        btnOrari.put("00:00", h24m00)


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
        btnOrari.get("00:00")!!.isEnabled = false

        btnOrari.get("6:30")!!.setBackgroundColor(Color.GRAY)
        btnOrari.get("7:00")!!.setBackgroundColor(Color.GRAY)
        btnOrari.get("7:30")!!.setBackgroundColor(Color.GRAY)
        btnOrari.get("8:00")!!.setBackgroundColor(Color.GRAY)
        btnOrari.get("8:30")!!.setBackgroundColor(Color.GRAY)
        btnOrari.get("9:00")!!.setBackgroundColor(Color.GRAY)
        btnOrari.get("9:30")!!.setBackgroundColor(Color.GRAY)
        btnOrari.get("10:00")!!.setBackgroundColor(Color.GRAY)
        btnOrari.get("10:30")!!.setBackgroundColor(Color.GRAY)
        btnOrari.get("11:00")!!.setBackgroundColor(Color.GRAY)
        btnOrari.get("11:30")!!.setBackgroundColor(Color.GRAY)
        btnOrari.get("12:00")!!.setBackgroundColor(Color.GRAY)
        btnOrari.get("12:30")!!.setBackgroundColor(Color.GRAY)
        btnOrari.get("13:00")!!.setBackgroundColor(Color.GRAY)
        btnOrari.get("13:30")!!.setBackgroundColor(Color.GRAY)
        btnOrari.get("14:00")!!.setBackgroundColor(Color.GRAY)
        btnOrari.get("14:30")!!.setBackgroundColor(Color.GRAY)
        btnOrari.get("15:00")!!.setBackgroundColor(Color.GRAY)
        btnOrari.get("15:30")!!.setBackgroundColor(Color.GRAY)
        btnOrari.get("16:00")!!.setBackgroundColor(Color.GRAY)
        btnOrari.get("16:30")!!.setBackgroundColor(Color.GRAY)
        btnOrari.get("17:00")!!.setBackgroundColor(Color.GRAY)
        btnOrari.get("17:30")!!.setBackgroundColor(Color.GRAY)
        btnOrari.get("18:00")!!.setBackgroundColor(Color.GRAY)
        btnOrari.get("18:30")!!.setBackgroundColor(Color.GRAY)
        btnOrari.get("19:00")!!.setBackgroundColor(Color.GRAY)
        btnOrari.get("19:30")!!.setBackgroundColor(Color.GRAY)
        btnOrari.get("20:00")!!.setBackgroundColor(Color.GRAY)
        btnOrari.get("20:30")!!.setBackgroundColor(Color.GRAY)
        btnOrari.get("21:00")!!.setBackgroundColor(Color.GRAY)
        btnOrari.get("21:30")!!.setBackgroundColor(Color.GRAY)
        btnOrari.get("22:00")!!.setBackgroundColor(Color.GRAY)
        btnOrari.get("22:30")!!.setBackgroundColor(Color.GRAY)
        btnOrari.get("23:00")!!.setBackgroundColor(Color.GRAY)
        btnOrari.get("23:30")!!.setBackgroundColor(Color.GRAY)
        btnOrari.get("00:00")!!.setBackgroundColor(Color.GRAY)
        val prenotazioni = DB_Handler_Reservation.getListOfReservations(
            giorno,
            campo,
            circolo,
            object : DB_Handler_Reservation.MyCallbackReservations {
                override fun onCallback(reservations: ArrayList<Prenotazione>?) {
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
                    btnOrari.get("00:00")!!.isEnabled = false

                    btnOrari.get("6:30")!!.setBackgroundColor(Color.BLUE)
                    btnOrari.get("7:00")!!.setBackgroundColor(Color.BLUE)
                    btnOrari.get("7:30")!!.setBackgroundColor(Color.BLUE)
                    btnOrari.get("8:00")!!.setBackgroundColor(Color.BLUE)
                    btnOrari.get("8:30")!!.setBackgroundColor(Color.BLUE)
                    btnOrari.get("9:00")!!.setBackgroundColor(Color.BLUE)
                    btnOrari.get("9:30")!!.setBackgroundColor(Color.BLUE)
                    btnOrari.get("10:00")!!.setBackgroundColor(Color.BLUE)
                    btnOrari.get("10:30")!!.setBackgroundColor(Color.BLUE)
                    btnOrari.get("11:00")!!.setBackgroundColor(Color.BLUE)
                    btnOrari.get("11:30")!!.setBackgroundColor(Color.BLUE)
                    btnOrari.get("12:00")!!.setBackgroundColor(Color.BLUE)
                    btnOrari.get("12:30")!!.setBackgroundColor(Color.BLUE)
                    btnOrari.get("13:00")!!.setBackgroundColor(Color.BLUE)
                    btnOrari.get("13:30")!!.setBackgroundColor(Color.BLUE)
                    btnOrari.get("14:00")!!.setBackgroundColor(Color.BLUE)
                    btnOrari.get("14:30")!!.setBackgroundColor(Color.BLUE)
                    btnOrari.get("15:00")!!.setBackgroundColor(Color.BLUE)
                    btnOrari.get("15:30")!!.setBackgroundColor(Color.BLUE)
                    btnOrari.get("16:00")!!.setBackgroundColor(Color.BLUE)
                    btnOrari.get("16:30")!!.setBackgroundColor(Color.BLUE)
                    btnOrari.get("17:00")!!.setBackgroundColor(Color.BLUE)
                    btnOrari.get("17:30")!!.setBackgroundColor(Color.BLUE)
                    btnOrari.get("18:00")!!.setBackgroundColor(Color.BLUE)
                    btnOrari.get("18:30")!!.setBackgroundColor(Color.BLUE)
                    btnOrari.get("19:00")!!.setBackgroundColor(Color.BLUE)
                    btnOrari.get("19:30")!!.setBackgroundColor(Color.BLUE)
                    btnOrari.get("20:00")!!.setBackgroundColor(Color.BLUE)
                    btnOrari.get("20:30")!!.setBackgroundColor(Color.BLUE)
                    btnOrari.get("21:00")!!.setBackgroundColor(Color.BLUE)
                    btnOrari.get("21:30")!!.setBackgroundColor(Color.BLUE)
                    btnOrari.get("22:00")!!.setBackgroundColor(Color.BLUE)
                    btnOrari.get("22:30")!!.setBackgroundColor(Color.BLUE)
                    btnOrari.get("23:00")!!.setBackgroundColor(Color.BLUE)
                    btnOrari.get("23:30")!!.setBackgroundColor(Color.BLUE)
                    btnOrari.get("00:00")!!.setBackgroundColor(Color.GRAY)

                    //Oscuro le prenotazioni già effettuate (se ci sono)
                    if (reservations != null) {
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
                                if (ora == 23 && min == 59) {
                                    btn = btnOrari.get("00:00")!!
                                    break
                                } else {
                                    if (min == 0) {
                                        btn = btnOrari.get(ora.toString() + ":00")!!
                                    } else {
                                        btn = btnOrari.get(ora.toString() + ":" + min.toString())!!
                                    }
                                }

                                btn.setBackgroundColor(Color.RED)
                                btn.isEnabled = false
                                //Aggiungo mezz'ora
                                if (min == 30) {
                                    if (ora == 23) {
                                        min = 59
                                    } else {
                                        min = 0
                                        ora += 1
                                    }
                                } else {
                                    min = 30
                                }
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
        btnOrari.get("00:00")!!.setOnClickListener(this)


        //Settiamo il comportamento sugli onClick



    }
    @SuppressLint("ResourceAsColor")
    fun firstClick(btntId: String) {
        oraInizioStr = btntId

        flagClick = true
        textView8.text = "Selezionare orario di fine"
        btnOrari.get(btntId)!!.isEnabled = false
        btnOrari.get(btntId)!!.setBackgroundColor(Color.CYAN)
        btnOrari.get("00:00")!!.isEnabled = true
        btnOrari.get("00:00")!!.setBackgroundColor(Color.BLUE)
        var ora = "6:30".split(":") as MutableList<String>
        while (oraInizioStr != ora[0] + ":" + ora[1]) {
            btnOrari.get(ora[0] + ":" + ora[1])!!.isEnabled = false
            btnOrari.get(ora[0] + ":" + ora[1])!!.setBackgroundColor(Color.GRAY)
            //aggiungo mezzo'ora
            if (ora[1] == "30") {
                if(ora[0]=="23"){
                    ora[1] = "59"
                }else{
                    ora[1] = "00"
                    var app = ora[0].toInt()
                    app+=1
                    ora[0] = app.toString()
                }

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
                btnOrari.get(cal.get(Calendar.HOUR_OF_DAY).toString() + ":" + minPren)!!.isEnabled = true
                btnOrari.get(cal.get(Calendar.HOUR_OF_DAY).toString() + ":" + minPren)!!.setBackgroundColor(
                    Color.BLUE
                )
            }else{
                if(cal.get(Calendar.HOUR_OF_DAY)==splitOraInizio[0].toInt() && minPren=="30" && splitOraInizio[1].toString()=="00"){
                    btnOrari.get(cal.get(Calendar.HOUR_OF_DAY).toString() + ":" + minPren)!!.isEnabled = true
                    btnOrari.get(cal.get(Calendar.HOUR_OF_DAY).toString() + ":" + minPren)!!.setBackgroundColor(
                        Color.BLUE
                    )
                }
            }
        }
        Annulla.isEnabled = true
        Annulla.setBackgroundColor(Color.BLUE)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun secondClick(btntId: String) {
        if(btntId=="00:00"){
            oraFineStr = "23:59"
            btnOrari.get("00:00")!!.setBackgroundColor(Color.CYAN)
            btnOrari.get("00:00")!!.isEnabled = false
        }else{
            oraFineStr = btntId
            btnOrari.get(btntId)!!.setBackgroundColor(Color.CYAN)
            btnOrari.get(btntId)!!.isEnabled = false
        }




        //Coloro di ciano tutti i bottoni che rappresentano la fascia oraria indicata
        var splitOraInizio = oraInizioStr.split(":")
        var oraInizioInt : Int
        var oraInizio = splitOraInizio[0]
        var minInizio = splitOraInizio[1]
        while(oraInizio+":"+minInizio != oraFineStr){
            if(oraInizio=="23" && minInizio=="59"){
                btnOrari.get("00:00")!!.isEnabled = false

            }else{
                btnOrari.get(oraInizio + ":" + minInizio)!!.isEnabled = false
                btnOrari.get(oraInizio + ":" + minInizio)!!.setBackgroundColor(Color.CYAN)
                //Aggiungo mezz'ora
                if(minInizio=="30"){
                    if(oraInizio=="23"){
                        minInizio = "59"
                    }else{
                        oraInizioInt = oraInizio.toInt()
                        oraInizioInt+=1
                        oraInizio = oraInizioInt.toString()
                        minInizio="00"
                    }

                }else{
                    minInizio = "30"
                }
            }

        }
        ConfermaOrario.isEnabled = true
        ConfermaOrario.setBackgroundColor(Color.GREEN)
    }
    fun resetta() {
        flagClick = false
        oraFineStr = ""
        oraInizioStr = ""
        textView8.text = "Selezionare orario di inizio"
        Annulla.isEnabled = false
        Annulla.setBackgroundColor(Color.GRAY)
        ConfermaOrario.isEnabled = false
        ConfermaOrario.setBackgroundColor(Color.GRAY)

        btnOrari.get("6:30")!!.setBackgroundColor(Color.BLUE)
        btnOrari.get("7:00")!!.setBackgroundColor(Color.BLUE)
        btnOrari.get("7:30")!!.setBackgroundColor(Color.BLUE)
        btnOrari.get("8:00")!!.setBackgroundColor(Color.BLUE)
        btnOrari.get("8:30")!!.setBackgroundColor(Color.BLUE)
        btnOrari.get("9:00")!!.setBackgroundColor(Color.BLUE)
        btnOrari.get("9:30")!!.setBackgroundColor(Color.BLUE)
        btnOrari.get("10:00")!!.setBackgroundColor(Color.BLUE)
        btnOrari.get("10:30")!!.setBackgroundColor(Color.BLUE)
        btnOrari.get("11:00")!!.setBackgroundColor(Color.BLUE)
        btnOrari.get("11:30")!!.setBackgroundColor(Color.BLUE)
        btnOrari.get("12:00")!!.setBackgroundColor(Color.BLUE)
        btnOrari.get("12:30")!!.setBackgroundColor(Color.BLUE)
        btnOrari.get("13:00")!!.setBackgroundColor(Color.BLUE)
        btnOrari.get("13:30")!!.setBackgroundColor(Color.BLUE)
        btnOrari.get("14:00")!!.setBackgroundColor(Color.BLUE)
        btnOrari.get("14:30")!!.setBackgroundColor(Color.BLUE)
        btnOrari.get("15:00")!!.setBackgroundColor(Color.BLUE)
        btnOrari.get("15:30")!!.setBackgroundColor(Color.BLUE)
        btnOrari.get("16:00")!!.setBackgroundColor(Color.BLUE)
        btnOrari.get("16:30")!!.setBackgroundColor(Color.BLUE)
        btnOrari.get("17:00")!!.setBackgroundColor(Color.BLUE)
        btnOrari.get("17:30")!!.setBackgroundColor(Color.BLUE)
        btnOrari.get("18:00")!!.setBackgroundColor(Color.BLUE)
        btnOrari.get("18:30")!!.setBackgroundColor(Color.BLUE)
        btnOrari.get("19:00")!!.setBackgroundColor(Color.BLUE)
        btnOrari.get("19:30")!!.setBackgroundColor(Color.BLUE)
        btnOrari.get("20:00")!!.setBackgroundColor(Color.BLUE)
        btnOrari.get("20:30")!!.setBackgroundColor(Color.BLUE)
        btnOrari.get("21:00")!!.setBackgroundColor(Color.BLUE)
        btnOrari.get("21:30")!!.setBackgroundColor(Color.BLUE)
        btnOrari.get("22:00")!!.setBackgroundColor(Color.BLUE)
        btnOrari.get("22:30")!!.setBackgroundColor(Color.BLUE)
        btnOrari.get("23:00")!!.setBackgroundColor(Color.BLUE)
        btnOrari.get("23:30")!!.setBackgroundColor(Color.BLUE)
        btnOrari.get("00:00")!!.setBackgroundColor(Color.GRAY)


        //Reimposto tuti i bottoi cpme cliccabolei
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
        btnOrari.get("00:00")!!.isEnabled = false
        //------------------
        var minFine: String
        var oraFine: String
        var min: Int
        var ora: Int
        var btn: Button
        val cal: Calendar = Calendar.getInstance()
        for (prenotazione in prenotazioni) {
            cal.time = prenotazione.oraInizio
            min = cal.get(Calendar.MINUTE)
            ora = cal.get(Calendar.HOUR_OF_DAY)
            cal.time = prenotazione.oraFine
            minFine = cal.get(Calendar.MINUTE).toString()
            oraFine = cal.get(Calendar.HOUR_OF_DAY).toString()
            while (ora.toString() + ":" + min.toString() != oraFine + ":" + minFine) {


                if(ora==23 && min == 59){
                    btn = btnOrari.get("00:00")!!
                    break
                }else{
                    if (min == 0) {
                        btn = btnOrari.get(ora.toString() + ":00")!!
                    } else {
                        btn = btnOrari.get(ora.toString() + ":" + min.toString())!!
                    }
                }
                btn.setBackgroundColor(Color.RED)
                btn.isEnabled = false

                //Aggiungo mezz'ora
                if (min == 30) {
                    if(ora==23){
                        min = 59
                    }else{
                        min = 0
                        ora += 1
                    }

                } else {
                    min = 30
                }
            }
        }
    }



    override fun onClick(v: View?) {
        @RequiresApi(Build.VERSION_CODES.O)
        when (v!!.id) {
            h6m30.id -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("6:30")
                } else {
                    //Bottone già cliccato
                    secondClick("6:30")
                }
            }
            h7m00.id -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("7:00")
                } else {
                    //Bottone già cliccato
                    secondClick("7:00")
                }
            }

            h7m30.id -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("7:30")
                } else {
                    //Bottone già cliccato
                    secondClick("7:30")
                }
            }
            h8m00.id -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("8:00")
                } else {
                    //Bottone già cliccato
                    secondClick("8:00")
                }
            }
            h8m30.id -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("8:30")
                } else {
                    //Bottone già cliccato
                    secondClick("8:30")
                }
            }
            h9m00.id -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("9:00")
                } else {
                    //Bottone già cliccato
                    secondClick("9:00")
                }
            }
            h9m30.id -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("9:30")
                } else {
                    //Bottone già cliccato
                    secondClick("9:30")
                }
            }
            h10m00.id -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("10:00")
                } else {
                    //Bottone già cliccato
                    secondClick("10:00")
                }
            }
            h10m30.id -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("10:30")
                } else {
                    //Bottone già cliccato
                    secondClick("10:30")
                }
            }
            h11m00.id -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("11:00")
                } else {
                    //Bottone già cliccato
                    secondClick("11:00")
                }
            }
            h11m30.id -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("11:30")
                } else {
                    //Bottone già cliccato
                    secondClick("11:30")
                }
            }
            h12m00.id -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("12:00")
                } else {
                    //Bottone già cliccato
                    secondClick("12:00")
                }
            }
            h12m30.id -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("12:30")
                } else {
                    //Bottone già cliccato
                    secondClick("12:30")
                }
            }
            h13m00.id -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("13:00")
                } else {
                    //Bottone già cliccato
                    secondClick("13:00")
                }
            }
            h13m30.id -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("13:30")
                } else {
                    //Bottone già cliccato
                    secondClick("13:30")
                }
            }
            h14m00.id -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("14:00")
                } else {
                    //Bottone già cliccato
                    secondClick("14:00")
                }
            }
            h14m30.id -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("14:30")
                } else {
                    //Bottone già cliccato
                    secondClick("14:30")
                }
            }
            h15m00.id -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("15:00")
                } else {
                    //Bottone già cliccato
                    secondClick("15:00")
                }
            }
            h15m30.id -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("15:30")
                } else {
                    //Bottone già cliccato
                    secondClick("15:30")
                }
            }
            h16m00.id -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("16:00")
                } else {
                    //Bottone già cliccato
                    secondClick("16:00")
                }
            }
            h16m30.id -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("16:30")
                } else {
                    //Bottone già cliccato
                    secondClick("16:30")
                }
            }
            h17m00.id -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("17:00")
                } else {
                    //Bottone già cliccato
                    secondClick("17:00")
                }
            }
            h17m30.id -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("17:30")
                } else {
                    //Bottone già cliccato
                    secondClick("17:30")
                }
            }
            h18m00.id -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("18:00")
                } else {
                    //Bottone già cliccato
                    secondClick("18:00")
                }
            }
            h18m30.id -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("18:30")
                } else {
                    //Bottone già cliccato
                    secondClick("18:30")
                }
            }
            h19m00.id -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("19:00")
                } else {
                    //Bottone già cliccato
                    secondClick("19:00")
                }
            }
            h19m30.id -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("19:30")
                } else {
                    //Bottone già cliccato
                    secondClick("19:30")
                }
            }
            h20m00.id -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("20:00")
                } else {
                    //Bottone già cliccato
                    secondClick("20:00")
                }
            }
            h20m30.id -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("20:30")
                } else {
                    //Bottone già cliccato
                    secondClick("20:30")
                }
            }
            h21m00.id -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("21:00")
                } else {
                    //Bottone già cliccato
                    secondClick("21:00")
                }
            }
            h21m30.id -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("21:30")
                } else {
                    //Bottone già cliccato
                    secondClick("21:30")
                }
            }
            h22m00.id -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("22:00")
                } else {
                    //Bottone già cliccato
                    secondClick("22:00")
                }
            }
            h22m30.id -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("22:30")
                } else {
                    //Bottone già cliccato
                    secondClick("22:30")
                }
            }
            h23m00.id -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("23:00")
                } else {
                    //Bottone già cliccato
                    secondClick("23:00")
                }
            }
            h23m30.id -> {
                if (flagClick == false) {
                    //Bottone non cliccato
                    firstClick("23:30")
                } else {
                    //Bottone già cliccato
                    secondClick("23:30")
                }
            }
            h24m00.id ->
                secondClick("00:00")
        }
    }


}



