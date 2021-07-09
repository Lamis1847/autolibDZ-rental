package com.sil1.autolibdz_rental.ui.view.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.utils.sharedPrefFile

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //status bar
        val window: Window = this@SplashScreenActivity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(
            this@SplashScreenActivity,
            R.color.palette_yellow
        )
        setContentView(R.layout.activity_splash_screen)

        start()

        val sharedPref = this.getSharedPreferences(
            sharedPrefFile, Context.MODE_PRIVATE)

        with(sharedPref?.edit()) {
            this?.putBoolean("first", false)
            this?.apply()
        }
    }

    fun start() {

        val sharedPref = this.getSharedPreferences(
            sharedPrefFile, Context.MODE_PRIVATE)

        val fTime = sharedPref.getBoolean("first", true)
        if (fTime) {
            val connected = sharedPref.getBoolean("connected",false)
            if (connected) {
                val intent = Intent(this,HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        } else {
            val connected = sharedPref.getBoolean("connected",false)
            if (connected) {
                val intent = Intent(this,HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}