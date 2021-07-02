package com.example.progetto_programmazionemobile.View


import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.progetto_programmazionemobile.Model.Campo
import com.example.progetto_programmazionemobile.R
import com.example.progetto_programmazionemobile.ViewModel.DB_Handler_Courts
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*


class Selezione_1 : AppCompatActivity()
{
    lateinit var autocompleteSport : AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selezione_1)
        val builder = MaterialDatePicker.Builder.datePicker()
        builder.setTitleText("Seleziona una data per prenotarti")
        val picker = builder.build()



        val data : TextView = findViewById(R.id.data)
        val imageData : ImageView = findViewById(R.id.imageViewData)
        val currentDate: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        data.text = currentDate

        imageData.setOnClickListener{
            picker.show(supportFragmentManager, picker.toString())
        }

        picker.addOnPositiveButtonClickListener {


            val split: List<String> = picker.headerText.split(" ")
            val day = split[0]
            val month = split[1]
            val year = split[2]



            if(month == "gen") { if(day < 10.toString()) { data.setText("0"+day+"-01"+"-"+year)} else data.setText(day+"-01"+"-"+year) }
            if(month == "feb") { if(day < 10.toString()) { data.setText("0"+day+"-02"+"-"+year)} else data.setText(day+"-02"+"-"+year) }
            if(month == "mar") { if(day < 10.toString()) { data.setText("0"+day+"-03"+"-"+year)} else data.setText(day+"-03"+"-"+year) }
            if(month == "apr") { if(day < 10.toString()) { data.setText("0"+day+"-04"+"-"+year)} else data.setText(day+"-04"+"-"+year) }
            if(month == "mag") { if(day < 10.toString()) { data.setText("0"+day+"-05"+"-"+year)} else data.setText(day+"-05"+"-"+year) }
            if(month == "giu") { if(day < 10.toString()) { data.setText("0"+day+"-06"+"-"+year)} else data.setText(day+"-06"+"-"+year) }
            if(month == "lug") { if(day < 10.toString()) { data.setText("0"+day+"-07"+"-"+year)} else data.setText(day+"-07"+"-"+year) }
            if(month == "ago") { if(day < 10.toString()) { data.setText("0"+day+"-08"+"-"+year)} else data.setText(day+"-08"+"-"+year) }
            if(month == "set") { if(day < 10.toString()) { data.setText("0"+day+"-09"+"-"+year)} else data.setText(day+"-09"+"-"+year) }
            if(month == "ott") { if(day < 10.toString()) { data.setText("0"+day+"-10"+"-"+year)} else data.setText(day+"-10"+"-"+year) }
            if(month == "nov") { if(day < 10.toString()) { data.setText("0"+day+"-11"+"-"+year)} else data.setText(day+"-11"+"-"+year) }
            if(month == "dic") { if(day < 10.toString()) { data.setText("0"+day+"-12"+"-"+year)} else data.setText(day+"-12"+"-"+year) }


        }

        data.setOnClickListener{
            picker.show(supportFragmentManager, picker.toString())
        }


/*
        val timeInizio : TextView = findViewById(R.id.oraInizio)
        val cal = Calendar.getInstance()
        if(cal.get(Calendar.MINUTE) < 30) {cal.set(Calendar.MINUTE, 30)}
        else {
            cal.add(Calendar.MINUTE, 30)
            cal.clear(Calendar.MINUTE)
        }
        timeInizio.text = SimpleDateFormat("HH:mm").format(cal.time)

        timeInizio.setOnClickListener{
            val timeSetListener = TimePickerDialog.OnTimeSetListener{ timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                if(cal.get(Calendar.MINUTE) < 30) {cal.set(Calendar.MINUTE, 30)}
                else {
                    cal.add(Calendar.MINUTE, 30)
                    cal.clear(Calendar.MINUTE)
                }

                timeInizio.text = SimpleDateFormat("HH:mm").format(cal.time)


            }

            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true). show()

        }
*/

        val confermaBtn : Button = findViewById(R.id.confermabtn)
        val sportText : EditText = findViewById(R.id.sport)
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

        autocompleteSport.onItemSelectedListener = object  : AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener{
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
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onClick(v: View?) {
                //Facciamo partire la funzione per la ricerca di campi per SPORT ( se lo sport è != null )
                if(sportText.text.toString() != ""){
                    DB_Handler_Courts.getCourtsBySport(sportText.text.toString(),
                        object : DB_Handler_Courts.MyCallbackCourts {
                            override fun onCallback(returnedCourts: ArrayList<Campo>?) {

                                val intent = Intent(this@Selezione_1, Selezione_2::class.java)
                                //intent.putExtra("giorno",giorno)
                                intent.putExtra("campiPerSport", returnedCourts)

                                startActivity(intent)

                                finish()

                            }
                        })
                }else {
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



                /*   intent.putExtra(
                    "giorno",
                    datePicker.dayOfMonth.toString() + datePicker.month.toString() + datePicker.year.toString()
                )

              */

                // intent.putExtra("orainizio",myTimePicker.hour.toString()+myTimePicker.minute.toString())
                //  intent.putExtra("orafine",myTimePicker2.hour.toString()+myTimePicker2.minute.toString())

                //Query di filtraggi in base a :
                //SPORT
                //ORA INIZIO - ORA FINE - GIONRO


            }

        })
        }


}