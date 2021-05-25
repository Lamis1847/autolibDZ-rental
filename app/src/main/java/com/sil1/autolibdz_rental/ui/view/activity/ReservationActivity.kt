package com.sil1.autolibdz_rental.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.sil1.autolibdz_rental.R

class ReservationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


   //     this.getSupportActionBar()?.hide()


        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_vehicules)
        val nav = findNavController(R.id.nav_host_fragment)
        //setupActionBarWithNavController(nav)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp()
    }

}

