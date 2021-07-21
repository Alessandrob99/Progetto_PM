package com.example.progetto_programmazionemobile.View


import android.content.*
import android.content.res.Configuration
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.location.LocationManagerCompat.isLocationEnabled
import com.example.progetto_programmazionemobile.R
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.CalendarConstraints.DateValidator
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.parcel.Parcelize
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


        val locale = Locale("IT")
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        applicationContext.resources.updateConfiguration(config, null)

        val bounds = CalendarConstraints.Builder()
            .setValidator(FutureDateValidator())
            .build()

        val builder = MaterialDatePicker.Builder.datePicker()
        builder.setCalendarConstraints(bounds)

        builder.setTitleText("Seleziona una data per prenotarti")
        val picker = builder.build()
        val data: TextView = findViewById(R.id.data)
        val imageData: ImageView = findViewById(R.id.imageViewData)
        val currentDate: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        data.text = currentDate


        data.setOnClickListener {
            picker.show(supportFragmentManager, picker.toString())
        }

        imageData.setOnClickListener {
            picker.show(supportFragmentManager, picker.toString())
        }

        picker.addOnPositiveButtonClickListener {

            val currentDate: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(
                Date()
            )
            val split: List<String> = picker.headerText.split(" ")
            var day = split[0]
            var month = split[1]
            val year = split[2]

            var day2 = split[0]
            var month2 = split[1]


            if (day2.toInt() < 10) {
                day2 = "0$day"
            }

            if (month2 == "gen") {
                month2 = "1"
            }
            if (month2 == "feb") {
                month2 = "2"
            }
            if (month2 == "mar") {
                month2 = "3"
            }
            if (month2 == "apr") {
                month2 = "4"
            }
            if (month2 == "mag") {
                month2 = "5"
            }
            if (month2 == "giu") {
                month2 = "6"
            }
            if (month2 == "lug") {
                month2 = "7"
            }
            if (month2 == "ago") {
                month2 = "8"
            }
            if (month2 == "set") {
                month2 = "9"
            }
            if (month2 == "ott") {
                month2 = "10"
            }
            if (month2 == "nov") {
                month2 = "11"
            }
            if (month2 == "dic") {
                month2 = "12"
            }

            val all = "$day2-$month2-$year"


            if (month == "gen" || month == "Gen") {
                if (day.toInt() < 10) {
                    data.setText("0" + day + "-01" + "-" + year)
                } else data.setText(day + "-01" + "-" + year)
            }
            if (month == "feb" || month == "Feb") {
                if (day.toInt() < 10) {
                    data.setText("0" + day + "-02" + "-" + year)
                } else data.setText(day + "-02" + "-" + year)
            }
            if (month == "mar" || month == "Mar") {
                if (day.toInt() < 10) {
                    data.setText("0" + day + "-03" + "-" + year)
                } else data.setText(day + "-03" + "-" + year)
            }
            if (month == "apr" || month == "Apr") {
                if (day.toInt() < 10) {
                    data.setText("0" + day + "-04" + "-" + year)
                } else data.setText(day + "-04" + "-" + year)
            }
            if (month == "mag" || month == "Mag") {
                if (day.toInt() < 10) {
                    data.setText("0" + day + "-05" + "-" + year)
                } else data.setText(day + "-05" + "-" + year)
            }
            if (month == "giu" || month == "Giu") {
                if (day.toInt() < 10) {
                    data.setText("0" + day + "-06" + "-" + year)
                } else data.setText(day + "-06" + "-" + year)
            }
            if (month == "lug" || month == "Lug") {
                if (day.toInt() < 10) {
                    data.setText("0" + day + "-07" + "-" + year)
                } else data.setText(day + "-07" + "-" + year)
            }
            if (month == "ago" || month == "Ago") {
                if (day.toInt() < 10) {
                    data.setText("0" + day + "-08" + "-" + year)
                } else data.setText(day + "-08" + "-" + year)
            }
            if (month == "set" || month == "Set") {
                if (day.toInt() < 10) {
                    data.setText("0" + day + "-09" + "-" + year)
                } else data.setText(day + "-09" + "-" + year)
            }
            if (month == "ott" || month == "Ott") {
                if (day.toInt() < 10) {
                    data.setText("0" + day + "-10" + "-" + year)
                } else data.setText(day + "-10" + "-" + year)
            }
            if (month == "nov" || month == "Nov") {
                if (day.toInt() < 10) {
                    data.setText("0" + day + "-11" + "-" + year)
                } else data.setText(day + "-11" + "-" + year)
            }
            if (month == "dic" || month == "Dic") {
                if (day.toInt() < 10) {
                    data.setText("0" + day + "-12" + "-" + year)
                } else data.setText(day + "-12" + "-" + year)
            }
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
                var locMan: LocationManager? =
                    getSystemService(Context.LOCATION_SERVICE) as LocationManager
                /** Controllo se ha attiva la geocalizzazione  **/
                if (locMan != null) {
                    if (isLocationEnabled(locMan)) {
                        if (sportText.text.toString() != "") {
                            val intent = Intent(this@Selezione_1, LoaderLocation::class.java)
                            intent.putExtra("sport", sportText.text.toString())
                            intent.putExtra("giorno", data.text)
                            startActivity(intent)
                            finish()
                        } else {
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

                    } else {
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


@Parcelize
class FutureDateValidator : CalendarConstraints.DateValidator {
    override fun isValid(date: Long): Boolean {
        return date > System.currentTimeMillis()
    }
}