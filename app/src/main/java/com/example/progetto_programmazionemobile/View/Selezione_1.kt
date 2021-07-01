package com.example.progetto_programmazionemobile.View

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.progetto_programmazionemobile.R
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.time.Month
import java.time.Year
import java.util.*

class Selezione_1 : AppCompatActivity(), DatePickerDialog.OnDateSetListener
{
    lateinit var autocompleteSport : AutoCompleteTextView
    private val TIME_PICKER_INTERVAL = 15
    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0

    var day = 0
    var month = 0
    var year = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selezione_1)

        pickDate()



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


        //val myTimePickerInizio : TimePicker = findViewById(R.id.OraInizio)
        //val myTimePickerFine : TimePicker = findViewById(R.id.OraFine)
        //val datePicker : DatePicker = findViewById(R.id.datePicker)
        val confermaBtn : Button = findViewById(R.id.confermabtn)
        val sportText : EditText = findViewById(R.id.sport)
        val arraySport = arrayOf("", "Calcetto", "Pallavolo", "Calcio", "Tennis", "Basket")

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
                //Si passano i parametri inseriti alla seconda activty di selezione

                val intent = Intent(applicationContext, Selezione_2::class.java)
                intent.putExtra("sport", "Calcio")
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


                startActivity(intent)
                finish()
            }

        })
        }

    private fun getDataCalendar(){
        val cal : Calendar = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
    }

    private fun pickDate() {
        val oggi : TextView = findViewById(R.id.data)
        oggi.setOnClickListener{
            getDataCalendar()

            DatePickerDialog(this, this, year, month, day).show()
        }
    }

    override fun onDateSet(view: DatePicker?, year:Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year

        getDataCalendar()
        val oggi : TextView = findViewById(R.id.data)
        oggi.text = "$savedDay-$savedMonth-$savedYear"

    }
}