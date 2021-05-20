package com.sil1.autolibdz_rental.utils

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ImageView
import com.sil1.autolibdz_rental.R

var userToken :String =""
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
    }


}