package com.sil1.autolibdz_rental.ui.view.activity.validation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.ui.view.fragment.profil.ProfilViewModel
import kotlinx.android.synthetic.main.activity_validation.*
import java.io.ByteArrayOutputStream


class ValidationActivity : AppCompatActivity() {

    private val PERMIS_INTENT = 1
    private val SELFIE_INTENT = 11

     var selfieBitmap:Bitmap? = null
     var permisBitmap:Bitmap? = null

    var tookSelfie = false
    var tookPermis = false

    lateinit var viewModel:ValidationVm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_validation)

        val config: MutableMap<String, String> = HashMap()

        config["cloud_name"] = "dkwni6a1d"
        config["api_key"] = "417846431126524"
        config["api_secret"] = "VI0hz_4ir15jp-Slkb_heb44-LI"

        MediaManager.init(this, config)


        viewModel = ViewModelProvider(this).get(ValidationVm::class.java)

        viewModel.callResult.observe(this,{

            if(it){
                startActivity(Intent(this@ValidationActivity, ValidationReadyActivity::class.java))
            }else{
                Toast.makeText(this,"please check your internet connection",Toast.LENGTH_SHORT).show()
            }
        })
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
        if (requestCode == PERMIS_INTENT && resultCode == RESULT_OK && data != null) {

            selfieBitmap = data.extras!!["data"] as Bitmap
            permis_pic.setImageResource(R.drawable.ic_done)
            permis_path.setTextColor(resources.getColor(R.color.palette_yellow))

            tookSelfie = true
        }else{
            Log.e("camera-permis", resultCode.toString())
        }

        if (requestCode == SELFIE_INTENT && resultCode == RESULT_OK && data != null) {
            permisBitmap = data.extras!!["data"] as Bitmap
            selfie_pic.setImageResource(R.drawable.ic_done)
            selfie_path.setTextColor(resources.getColor(R.color.palette_yellow))

            tookPermis = true
        }else{
            Log.e("camera-selfie", resultCode.toString())
        }
    }

    private fun checkPermission() {
        val perms = arrayOf(
            Manifest.permission.CAMERA
        )
        val permsRequestCode = 300;
        ActivityCompat.requestPermissions(this, perms, permsRequestCode)

    }

    fun continuer(view: View) {

        var permisUrl = ""
        var selfieUrl = ""
        var selfieId = ""
        var permisId = ""

        Log.e("upload-", "upload : starting")

        if(tookPermis && tookSelfie){

            val stream = ByteArrayOutputStream()
            selfieBitmap!!.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray: ByteArray = stream.toByteArray()

            MediaManager.get().upload(byteArray).callback(object : UploadCallback {
                override fun onStart(requestId: String?) {
                    Log.e("upload-", "upload : ${requestId!!}")
                }

                override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {}

                override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
                    Log.e("upload-", "upload : ${resultData!!}")

                    selfieUrl = resultData!!["secure_url"].toString()
                    selfieId = resultData!!["public_id"].toString()

                    val newstream = ByteArrayOutputStream()
                    permisBitmap!!.compress(Bitmap.CompressFormat.PNG, 100, newstream)
                    val newbyteArray: ByteArray = newstream.toByteArray()

                    MediaManager.get().upload(newbyteArray).callback(object : UploadCallback {
                        override fun onStart(requestId: String?) {}

                        override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {}

                        override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {

                            permisUrl = resultData!!["secure_url"].toString()
                            permisId = resultData!!["public_id"].toString()
                            Log.e("upload-call",permisUrl+"$requestId  $selfieUrl    $selfieId")
                            viewModel.envoyerValidationDemande(245,selfieUrl,permisUrl,permisId,selfieId)

                        }

                        override fun onError(requestId: String?, error: ErrorInfo?) {}

                        override fun onReschedule(requestId: String?, error: ErrorInfo?) {}
                    }).dispatch()
                }

                override fun onError(requestId: String?, error: ErrorInfo?) {
                    Log.e("upload-", "upload : ${error!!}")
                }

                override fun onReschedule(requestId: String?, error: ErrorInfo?) {}
            }).dispatch()

        }else{
            Toast.makeText(this,"please make sure to take a picture of you and your permis",Toast.LENGTH_SHORT).show()
        }


        //startActivity(Intent(this, ValidationReadyActivity::class.java))
    }
}