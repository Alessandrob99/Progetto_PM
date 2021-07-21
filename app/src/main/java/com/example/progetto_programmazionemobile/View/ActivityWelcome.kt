package com.example.progetto_programmazionemobile.View

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.progetto_programmazionemobile.R
import kotlinx.android.synthetic.main.loader_location.view.*

class ActivityWelcome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)


        val window: Window = this.getWindow()
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.white))


        val quick : TextView = findViewById(R.id.quick)
        val play : TextView = findViewById(R.id.play)
        val anim : Animation = AnimationUtils.loadAnimation(applicationContext,R.anim.welcome_animation)
        val anim2 : Animation = AnimationUtils.loadAnimation(applicationContext,R.anim.welcome_animation2)

        quick.setAnimation(anim)
        play.setAnimation(anim2)

        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                Thread.sleep(1000)
                finish()
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }

        })

        anim2.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {

            }

            override fun onAnimationRepeat(p0: Animation?) {

            }

        })
    }
}