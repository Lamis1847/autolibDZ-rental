package com.sil1.autolibdz_rental.ui.view.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.ui.view.fragment.facture_carte.FactureCarteFragment

class CheckoutActivity : AppCompatActivity() {

    companion object {
        // The code that denotes the request for location permissions
        private const val REQUEST_CODE_LOCATION = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val window: Window = this@CheckoutActivity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(
            this@CheckoutActivity,
            R.color.palette_yellow
        )

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            // REQUEST_CODE_LOCATION should be defined on your app level
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_LOCATION)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_LOCATION && grantResults.isNotEmpty()
            && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            throw RuntimeException("Location services are required in order to " + "connect to a reader.")
        }
    }
}