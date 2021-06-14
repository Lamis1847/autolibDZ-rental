package com.sil1.autolibdz_rental.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.sil1.autolibdz_rental.R


var userToken :String =""
val sharedPrefFile: String = "kotlinsharedpreference"
var idTokenUser :String = ""

class utils {
    companion object {
        //méthode pour show and hide passwords
         fun showPassword(a: EditText, b: ImageView, isShow: Boolean) {
            if (isShow) {
                // To show the password
                a.transformationMethod = HideReturnsTransformationMethod.getInstance()
                b.setImageResource(R.drawable.ic_hide_passsword)
            } else {
                // To hide the password
                a.transformationMethod = PasswordTransformationMethod.getInstance()
                b.setImageResource(R.drawable.ic_show_password)
            }
            // This line of code to put the pointer at the end of the password string
            a.setSelection(a.text.toString().length)
        }
        fun getBitmapFromVectorDrawable(context: Context?, drawableId: Int): Bitmap? {
            var drawable = context?.let { ContextCompat.getDrawable(it, drawableId) }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                drawable = DrawableCompat.wrap(drawable!!).mutate()
            }
            val bitmap = Bitmap.createBitmap(
                drawable!!.intrinsicWidth,
                drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
            drawable.draw(canvas)
            return bitmap
        }
    }




}