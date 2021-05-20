package com.example.progetto_programmazionemobile.View

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.progetto_programmazionemobile.R

class LogoutPopUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        setContentView(R.layout.activity_logout_pop_up)

        val btnYes = findViewById<Button>(R.id.popup_window_yes)
        btnYes.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                //LogOut
                val intent = Intent(this@LogoutPopUp, MainActivity::class.java)
                startActivity(intent)
            }
        })

        val btnNo = findViewById<Button>(R.id.popup_window_no)
        btnNo.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                finish()
            }
        })
    }
}