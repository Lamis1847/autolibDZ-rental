package com.sil1.autolibdz_rental.ui.view.activity.validation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.ui.view.activity.LoginActivity
import kotlinx.android.synthetic.main.activity_validation_ready.*

class ValidationReadyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_validation_ready)
        validationUpload.setOnClickListener(){
            val myIntent = Intent(this, LoginActivity::class.java)
            this.startActivity(myIntent)
        }
    }

}