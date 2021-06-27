package com.sil1.autolibdz_rental.ui.view.activity.validation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.sil1.autolibdz_rental.R
import kotlinx.android.synthetic.main.activity_validation.*


class ValidationActivity : AppCompatActivity() {

    private val PERMIS_INTENT = 1
    private val SELFIE_INTENT = 11

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_validation)
    }

    fun takeSelfiePic(view: View) {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            checkPermission()
        } else{

            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, SELFIE_INTENT)
        }

    }
    fun takePermisPic(view: View) {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            checkPermission()
        } else{

            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, PERMIS_INTENT)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PERMIS_INTENT && resultCode == RESULT_OK) {
            val photo = data!!.extras!!["data"] as Bitmap?
            permis_pic.setImageResource(R.drawable.ic_done)
            permis_path.setTextColor(resources.getColor(R.color.palette_yellow))
        }else{
            Log.e("camera-permis",resultCode.toString())
        }

        if (requestCode == SELFIE_INTENT && resultCode == RESULT_OK) {
            val photo = data!!.extras!!["data"] as Bitmap?
            selfie_pic.setImageResource(R.drawable.ic_done)
            selfie_path.setTextColor(resources.getColor(R.color.palette_yellow))
        }else{
            Log.e("camera-selfie",resultCode.toString())
        }
    }

    private fun checkPermission() {
        val perms = arrayOf(
            Manifest.permission.CAMERA
        )
        val permsRequestCode = 300;
        ActivityCompat.requestPermissions(this, perms, permsRequestCode)

    }
}