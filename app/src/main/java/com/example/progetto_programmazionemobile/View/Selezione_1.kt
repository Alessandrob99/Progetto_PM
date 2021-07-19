package com.example.progetto_programmazionemobile.View


import android.annotation.SuppressLint
import android.content.*
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.location.LocationManagerCompat.isLocationEnabled
import com.example.progetto_programmazionemobile.Model.Campo
import com.example.progetto_programmazionemobile.R
import com.example.progetto_programmazionemobile.ViewModel.DB_Handler_Courts
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*


class Selezione_1 : AppCompatActivity() {
    lateinit var autocompleteSport: AutoCompleteTextView
    lateinit var locationManager: LocationManager
    private var hasGps = false
    private var hasNetwork = false
    private var locationGps: Location? = null
    private var locationNetwork: Location? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selezione_1)

        val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(arg0: Context?, intent: Intent) {
                val action = intent.action
                if (action == "finish_activity") {
                    finish()
                    // DO WHATEVER YOU WANT.
                }
            }
        }
        registerReceiver(broadcastReceiver, IntentFilter("logout"))
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

        val builder = MaterialDatePicker.Builder.datePicker()
        builder.setTitleText("Seleziona una data per prenotarti")
        val picker = builder.build()
        val data: TextView = findViewById(R.id.data)
        val imageData: ImageView = findViewById(R.id.imageViewData)
        val currentDate: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        data.text = currentDate






        imageData.setOnClickListener {
            picker.show(supportFragmentManager, picker.toString())
        }

        picker.addOnPositiveButtonClickListener {

            val currentDate: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
            val split: List<String> = picker.headerText.split(" ")
            val day = split[0]
            val month = split[1]
            val year = split[2]
            val all = "$day-$month-$year"

            if(all.compareTo(currentDate) < 0)
            {
                val builder: AlertDialog.Builder =
                    AlertDialog.Builder(this@Selezione_1)
                builder.setTitle("Errore")
                builder.setMessage("Devi allenarti oggi! Ieri è troppo tardi.")
                builder.setPositiveButton(
                    "OK",
                    object : DialogInterface.OnClickListener {
                        override fun onClick(
                            dialog: DialogInterface?,
                            which: Int
                        ) {
                        }
                    })
                val alertDialog = builder.create()
                alertDialog.show()
            }
            else {


                if (month == "gen") {
                    if (day.toInt() < 10) {
                        data.setText("0" + day + "-01" + "-" + year)
                    } else data.setText(day + "-01" + "-" + year)
                }
                if (month == "feb") {
                    if (day.toInt() < 10) {
                        data.setText("0" + day + "-02" + "-" + year)
                    } else data.setText(day + "-02" + "-" + year)
                }
                if (month == "mar") {
                    if (day.toInt() < 10) {
                        data.setText("0" + day + "-03" + "-" + year)
                    } else data.setText(day + "-03" + "-" + year)
                }
                if (month == "apr") {
                    if (day.toInt() < 10) {
                        data.setText("0" + day + "-04" + "-" + year)
                    } else data.setText(day + "-04" + "-" + year)
                }
                if (month == "mag") {
                    if (day.toInt() < 10) {
                        data.setText("0" + day + "-05" + "-" + year)
                    } else data.setText(day + "-05" + "-" + year)
                }
                if (month == "giu") {
                    if (day.toInt() < 10) {
                        data.setText("0" + day + "-06" + "-" + year)
                    } else data.setText(day + "-06" + "-" + year)
                }
                if (month == "lug") {
                    if (day.toInt() < 10) {
                        data.setText("0" + day + "-07" + "-" + year)
                    } else data.setText(day + "-07" + "-" + year)
                }
                if (month == "ago") {
                    if (day.toInt() < 10) {
                        data.setText("0" + day + "-08" + "-" + year)
                    } else data.setText(day + "-08" + "-" + year)
                }
                if (month == "set") {
                    if (day.toInt() < 10) {
                        data.setText("0" + day + "-09" + "-" + year)
                    } else data.setText(day + "-09" + "-" + year)
                }
                if (month == "ott") {
                    if (day.toInt() < 10) {
                        data.setText("0" + day + "-10" + "-" + year)
                    } else data.setText(day + "-10" + "-" + year)
                }
                if (month == "nov") {
                    if (day.toInt() < 10) {
                        data.setText("0" + day + "-11" + "-" + year)
                    } else data.setText(day + "-11" + "-" + year)
                }
                if (month == "dic") {
                    if (day.toInt() < 10) {
                        data.setText("0" + day + "-12" + "-" + year)
                    } else data.setText(day + "-12" + "-" + year)
                }
            }




        }

        data.setOnClickListener {
            picker.show(supportFragmentManager, picker.toString())
        }

        val confermaBtn: Button = findViewById(R.id.confermabtn)
        val sportText: EditText = findViewById(R.id.sport)
        val arraySport = arrayOf(
            "",
            "Calcetto",
            "Pallavolo",
            "Calcio",
            "Tennis",
            "Basket",
            "Paddle"
        )

        autocompleteSport = findViewById(R.id.sport) as AutoCompleteTextView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arraySport)
        autocompleteSport.setText(adapter.getItem(0).toString(), false)
        autocompleteSport.setAdapter(adapter)

        autocompleteSport.onItemSelectedListener =
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


        confermaBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                var locMan: LocationManager? = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                /** Controllo se ha attiva la geocalizzazione  **/
                if (locMan != null)
                {
                    if (isLocationEnabled(locMan))
                    {
                        if (sportText.text.toString() != "")
                        {
                            val intent = Intent(this@Selezione_1, LoaderLocation::class.java)
                            intent.putExtra("sport", sportText.text.toString())
                            intent.putExtra("giorno", data.text)
                            startActivity(intent)
                            finish()
                        }
                        else{
                            val builder: AlertDialog.Builder =
                                AlertDialog.Builder(this@Selezione_1)
                            builder.setTitle("Errore")
                            builder.setMessage("Inserisci uno sport")
                            builder.setPositiveButton(
                                "OK",
                                object : DialogInterface.OnClickListener {
                                    override fun onClick(
                                        dialog: DialogInterface?,
                                        which: Int
                                    ) {
                                        //Click sull'avviso di sport non inserito
                                    }
                                })
                            val alertDialog = builder.create()
                            alertDialog.show()
                        }

                    }
                    else {
                        val builder: AlertDialog.Builder =
                            AlertDialog.Builder(this@Selezione_1)
                        builder.setTitle("Errore")
                        builder.setMessage("E' necessario abilitare la geocalizzazione per questa funzione")
                        builder.setPositiveButton(
                            "OK",
                            object : DialogInterface.OnClickListener {
                                override fun onClick(
                                    dialog: DialogInterface?,
                                    which: Int
                                ) {
                                    //Click sull'avviso di sport non inserito
                                }
                            })
                        val alertDialog = builder.create()
                        alertDialog.show()
                    }
                }

            }
        })
    }
}






