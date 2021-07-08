package com.sil1.autolibdz_rental.ui.view.activity.validation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.ui.view.activity.LoginActivity
import kotlinx.android.synthetic.main.activity_validation_ready.*

class ValidationReadyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            val myIntent = Intent(this, LoginActivity::class.java)
            this.startActivity(myIntent)
        }
    }

}