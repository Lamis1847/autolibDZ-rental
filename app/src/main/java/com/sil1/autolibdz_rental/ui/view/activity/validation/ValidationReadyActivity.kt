package com.sil1.autolibdz_rental.ui.view.activity.validation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.ui.view.activity.HomeActivity
import com.sil1.autolibdz_rental.ui.view.activity.LoginActivity
import com.sil1.autolibdz_rental.utils.sharedPrefFile
import kotlinx.android.synthetic.main.activity_validation_ready.*

class ValidationReadyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = this.getSharedPreferences(
            sharedPrefFile, Context.MODE_PRIVATE
        )

        val connected = sharedPref.getBoolean("connected",false)
        //status bar
        val window: Window = this@ValidationReadyActivity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(
            this@ValidationReadyActivity,
            R.color.palette_yellow
        )

        setContentView(R.layout.activity_validation_ready)

        validationUpload.setOnClickListener(){
            if(connected){
                val myIntent = Intent(this, HomeActivity::class.java)
                this.startActivity(myIntent)


            }
            else {
                val myIntent = Intent(this, LoginActivity::class.java)
                this.startActivity(myIntent)

            }
        }
    }

}