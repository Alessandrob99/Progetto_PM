package com.example.progetto_programmazionemobile.View

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import com.example.progetto_programmazionemobile.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class Selezione_1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selezione_1)

        val myTimePicker : TimePicker = findViewById(R.id.timePicker)
        val myTimePicker2 : TimePicker = findViewById(R.id.timePicker2)
        val datePicker : DatePicker = findViewById(R.id.datePicker)
        val confermaBtn : Button = findViewById(R.id.confermabtn)
        val sportText : EditText = findViewById(R.id.sport)

        confermaBtn.setOnClickListener(object : View.OnClickListener{
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onClick(v: View?) {
                //Si passano i parametri inseriti alla seconda activty di selezione

                val intent = Intent(applicationContext, Selezione_2::class.java)
                intent.putExtra("sport","Calcio")
                intent.putExtra("giorno",datePicker.dayOfMonth.toString()+datePicker.month.toString())
                intent.putExtra("orainizio",myTimePicker.hour.toString()+myTimePicker.minute.toString())
                intent.putExtra("orafine",myTimePicker2.hour.toString()+myTimePicker2.minute.toString())
                startActivity(intent)

            }
        })



        myTimePicker.setIs24HourView(true)
        myTimePicker2.setIs24HourView(true)
    }
}