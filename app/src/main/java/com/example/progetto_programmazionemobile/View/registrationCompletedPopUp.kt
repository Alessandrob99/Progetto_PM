package com.example.progetto_programmazionemobile.View

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.progetto_programmazionemobile.R
import com.example.progetto_programmazionemobile.ViewModel.Auth_Handler

class registrationCompletedPopUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        setContentView(R.layout.activity_registration_completed_pop_up)

        val btnOk = findViewById<Button>(R.id.popup_window_ok)
        btnOk.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                //REDIRECT ALLA MAIN ACTIVITY
                val intent = Intent(this@registrationCompletedPopUp, MainActivity::class.java)
                startActivity(intent)
            }
        })
    }

    override fun onBackPressed() {
        val intent = Intent(this@registrationCompletedPopUp, MainActivity::class.java)
        startActivity(intent)
    }
}